package de.finances.application.service;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import de.finances.application.model.Transaction;

public class TransactionService extends AbstractEntityService<Transaction> {

	public void copyTransaction(final List<Transaction> transactionsToCopy) {
		transactionsToCopy.forEach(this::copyTransaction);
	}

	public void copyTransaction(final Transaction transactionToCopy) {
		final Transaction found = this.find(transactionToCopy);
		this.getEm().detach(found);
		found.setId(null);
		found.setCreated(found.getCreated().plusMonths(1));
		this.update(found);
	}

	public Set<Month> findAllUsesdMonth(final int year) {

		final LocalDate firstDayOfYear = LocalDate.of(year, 1, 1);

		return this.getEm().createNamedQuery(Transaction.FIND_ALL_MONTH, LocalDate.class)
				.setParameter("start", firstDayOfYear)
				.setParameter("end", firstDayOfYear.with(TemporalAdjusters.lastDayOfYear())).getResultList().stream()
				.map(LocalDate::getMonth).sorted().collect(Collectors.toSet());
	}

	public List<Integer> findAllUsesdYears() {
		return this.getEm().createNamedQuery(Transaction.FIND_ALL_YEARS, LocalDate.class).getResultList().stream()
				.map(LocalDate::getYear).distinct().collect(Collectors.toList());
	}

	public List<Transaction> getTransactionsByMonthAndYear(final int year, final Month month) {
		// the day is not interesting, just use 1 as dummy
		final LocalDate initial = LocalDate.of(year, month, 1);
		return this.findWithNamedQuery(Transaction.TRANSACTION_BY_MONTH,
				QueryParameter.with("start", initial.withDayOfMonth(1))
						.and("end", initial.withDayOfMonth(initial.lengthOfMonth())).parameters());
	}

}
