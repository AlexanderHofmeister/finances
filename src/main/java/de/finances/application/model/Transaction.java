package de.finances.application.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQueries(value = {
    @NamedQuery(name = Transaction.TRANSACTION_BY_MONTH, query = "SELECT t FROM Transaction t WHERE t.created BETWEEN :start AND :end"),
    @NamedQuery(name = Transaction.FIND_ALL_MONTH,
            query = "SELECT DISTINCT t.created FROM Transaction t where t.created  BETWEEN :start and :end ORDER BY t.created"),
    @NamedQuery(name = Transaction.FIND_ALL_YEARS, query = "SELECT DISTINCT t.created FROM Transaction t ORDER BY t.created") })
@ToString
public class Transaction extends BaseEntity {

  private static final long serialVersionUID = 1L;

  public static final String TRANSACTION_BY_MONTH = "transactionByMonth";

  public static final String FIND_ALL_MONTH = "findAllMonth";

  public static final String FIND_ALL_YEARS = "findAllYears";

  private String label;

  @Enumerated(EnumType.STRING)
  private Category category;

  private LocalDate created;

  private BigDecimal value = BigDecimal.ZERO;

  private TransactionType type;

}
