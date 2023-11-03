package org.code.bluetick.persistence.model;

import io.hypersistence.utils.hibernate.type.array.EnumArrayType;
import io.hypersistence.utils.hibernate.type.array.internal.AbstractArrayType;
import jakarta.persistence.*;
import lombok.*;
import org.code.bluetick.enums.Destination;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "travel_detail", schema = "crm")
@Builder
@Getter@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name="travelDetailSeq", sequenceName = "TRAVEL_DETAIL_SEQ", allocationSize = 1)
public class TravelDetail implements Serializable {
    @Id
    @Column(unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "travelDetailSeq")
    private Long id;

    @Column(name = "departure_city", nullable = false)
    private String departureCity;

    @Column(name = "nationality", nullable = false)
    private String nationality;

    @Column(name = "travelDate", nullable = false)
    private LocalDate travelDate;

    @Column(name = "rooms")
    private short rooms;

    @Column(name = "totalNights")
    private short totalNights;

    @Enumerated(EnumType.STRING)
    @Column(name = "destinations", columnDefinition = "crm.destination[]", nullable = false)
    @Type(value = EnumArrayType.class, parameters = @org.hibernate.annotations.Parameter(
            name = AbstractArrayType.SQL_ARRAY_TYPE,
            value = "crm.destination"
    ))
    private Destination[] destinations;

    @ToString.Exclude
    @Singular
    @OneToMany(mappedBy = "travelDetail", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Traveller> travellers;

    public void addTraveller(Traveller traveller) {
        if(travellers == null) {
            travellers = new ArrayList<>();
        }
        travellers.add(traveller);
        traveller.setTravelDetail(this);
    }

    public void removeTraveller(Traveller traveller) {
        travellers.remove(traveller);
        traveller.setTravelDetail(null);
    }
}
