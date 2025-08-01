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
    @jakarta.validation.constraints.NotNull(message = "Departure city must be specified")
    @jakarta.validation.constraints.Size(min = 2, max = 50, message = "Departure city must be between 2 and 50 characters")
    private String departureCity;

    @Column(name = "nationality", nullable = false)
    @jakarta.validation.constraints.NotNull(message = "Nationality must be specified")
    @jakarta.validation.constraints.Size(min = 2, max = 50, message = "Nationality must be between 2 and 50 characters")
    private String nationality;

    @Column(name = "travelDate", nullable = false)
    @jakarta.validation.constraints.NotNull(message = "Travel date must be specified")
    @jakarta.validation.constraints.Future(message = "Travel date must be in the future")
    private LocalDate travelDate;

    @Column(name = "rooms")
    @jakarta.validation.constraints.Min(value = 1, message = "At least 1 room must be selected")
    @jakarta.validation.constraints.Max(value = 10, message = "Maximum 10 rooms allowed")
    private short rooms;

    @Column(name = "totalNights")
    @jakarta.validation.constraints.Min(value = 1, message = "At least 1 night must be selected")
    @jakarta.validation.constraints.Max(value = 30, message = "Maximum 30 nights allowed")
    private short totalNights;

    @Enumerated(EnumType.STRING)
    @Column(name = "destinations", columnDefinition = "crm.destination[]", nullable = false)
    @Type(value = EnumArrayType.class, parameters = @org.hibernate.annotations.Parameter(
            name = AbstractArrayType.SQL_ARRAY_TYPE,
            value = "crm.destination"
    ))
    @jakarta.validation.constraints.NotNull(message = "Destinations must be specified")
    @jakarta.validation.constraints.Size(min = 1, message = "At least one destination must be selected")
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
