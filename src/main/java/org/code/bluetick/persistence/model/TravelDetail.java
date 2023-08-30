/*
package org.code.bluetick.persistence.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.code.bluetick.enums.Destination;
import org.code.bluetick.enums.PersonType;

import java.time.LocalDate;
import java.util.List;

@Getter@Setter
@Entity
@Builder
@NoArgsConstructor
@Table(name = "travel_detail")
@AllArgsConstructor
@SequenceGenerator(name="travelDetailSeq", sequenceName = "TRAVEL_DETAIL_SEQ", allocationSize = 1)
public class TravelDetail {
    @Id
    @Column(unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "travelDetailSeq")
    private Long id;

    @Column(name = "departure_city", nullable = false)
    @NotNull(message = "Departure City must be specified.")
    private String departureCity;

    @Column(name = "nationality", nullable = false)
    @NotNull(message = "Nationality must be specified.")
    private String nationality;

    @NotNull
    @Column(name = "travelDate", nullable = false)
    private LocalDate travelDate;

    @Min(value = 1, message = "Minimum 1 room should be booked")
    @Max(value = 10, message = "Maximum 10 rooms should be booked in a single booking")
    @Column(name = "rooms")
    private int rooms;

    @Min(value = 1, message = "Minimum 1N/2D should be booked")
    @Max(value = 20, message = "Maximum 20N/21D should be booked in a single booking")
    @Column(name = "totalNights")
    private int totalNights;

    @Singular
    @ElementCollection()
    @CollectionTable(name = "destinations", joinColumns = @JoinColumn(name = "destination_id"))
    @Column(name = "destination", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<Destination> destinations;

    @Singular
    @ElementCollection()
    @CollectionTable(name = "person_types", joinColumns = @JoinColumn(name = "person_type_id"))
    @Column(name = "totalGuests", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<PersonType> totalGuests;
}
 */
