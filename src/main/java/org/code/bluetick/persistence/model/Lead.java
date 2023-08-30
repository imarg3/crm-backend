/*
package org.code.bluetick.model;

import jakarta.persistence.*;
import lombok.*;
import org.code.bluetick.enums.LeadStatus;

import java.time.LocalDate;

@Getter@Setter
@Entity
@Builder
@NoArgsConstructor
@Table(name = "lead")
@AllArgsConstructor
@SequenceGenerator(name="leadSeq", sequenceName = "LEAD_SEQ", allocationSize = 1)
public class Lead {

    @Id
    @Column(unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leadSeq")
    private Long id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @NonNull
    private LocalDate createdDate = LocalDate.now();

    @OneToOne
    @JoinColumn(name = "travel_detail_id")
    private TravelDetail travelDetail;

    @Enumerated(EnumType.STRING)
    private LeadStatus status;
}
*/