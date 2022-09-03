package wit.shortterm1.kkoowoon.domain.workout.persist;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.workout.dto.WeightSetDto;
import wit.shortterm1.kkoowoon.global.common.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@Table(name = "weightSet")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WeightSet extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weight_set_id")
    private Long id;

    @Column(name = "sett")
    private int sett;

    @Column(name = "reps")
    private int reps;

    @Column(name = "set_weight")
    private double setWeight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weight_id")
    private Weight weight;

    private WeightSet(int sett, int reps, double setWeight, Weight weight) {
        this.sett = sett;
        this.reps = reps;
        this.setWeight = setWeight;
        this.weight = weight;
    }

    public static WeightSet of(int sett, int reps, double setWeight, Weight weight) {
        return new WeightSet(sett, reps, setWeight, weight);
    }

    public void updateWeightSet(WeightSetDto weightSetDto) {
        sett = weightSetDto.getSett();
        reps = weightSetDto.getReps();
        setWeight = weightSetDto.getSetWeight();
    }
}
