package dev.mananhemani.markethub.Models;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @ToString.Exclude
    @Column(length = 20,name = "role_name")
    @Enumerated(EnumType.STRING)
    private AppRole roleName;
}
