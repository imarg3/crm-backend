package org.code.bluetick.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import org.code.bluetick.enums.PersonType;
import org.hibernate.annotations.ColumnTransformer;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@Entity
@Table(name = "traveller", schema = "crm")
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name="travellerSeq", sequenceName = "TRAVELLER_SEQ", allocationSize = 1)
public class Traveller implements Serializable {
    @Id
    @Column(unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "travellerSeq")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name="age")
    private short age;

    // @ElementCollection()
    // @CollectionTable(name = "person_types", joinColumns = @JoinColumn(name = "person_type_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "person_type", columnDefinition = "crm.person_type", nullable = false)
    @ColumnTransformer(write = "?::crm.person_type")
    private PersonType personType;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "travel_detail_id")
    private TravelDetail travelDetail;

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Traveller)) return false;
        return id != null && id.equals(((Traveller)obj).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
