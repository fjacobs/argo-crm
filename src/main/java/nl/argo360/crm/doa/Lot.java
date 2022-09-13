package nl.argo360.crm.doa;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;


@Builder
@Entity
@Getter
@Setter
@ToString
@Table(name = "lots")
@NoArgsConstructor
@AllArgsConstructor
public class Lot {

    @Id
    @Column(name = "lot_id")
    @EqualsAndHashCode.Include
    private Integer id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "account_company_name")
    private Account account;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Lot lot = (Lot) o;
        return id != null && Objects.equals(id, lot.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
