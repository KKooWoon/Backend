package wit.shortterm1.kkoowoon.domain.workout.persist;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.global.common.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "food")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Food extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "food_weight")
    private double foodWeight;

    @Column(name = "food_calorie")
    private double foodCalorie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diet_id")
    private Diet diet;

    private Food(String name, double foodWeight, double foodCalorie, Diet diet) {
        this.name = name;
        this.foodWeight = foodWeight;
        this.foodCalorie = foodCalorie;
        this.diet = diet;
    }

    public static Food of(String name, double foodWeight, double foodCalorie, Diet diet) {
        return new Food(name, foodWeight, foodCalorie, diet);
    }
}
