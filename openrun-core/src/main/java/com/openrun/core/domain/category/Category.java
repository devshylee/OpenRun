package com.openrun.core.domain.category;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "category")
public class Category {

    /** 카테고리 고유 ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 카테고리 이름 */
    @Column(nullable = false, length = 100)
    private String name;

    /** 상위 카테고리 ID (자기 참조) */
    @Column(name = "parent_id")
    private Integer parentId;
}
