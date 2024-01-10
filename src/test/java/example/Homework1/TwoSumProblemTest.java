package example.Homework1;

import org.example.Homework1.TwoSumProblem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;


public class TwoSumProblemTest {

    //Test di prova del passo 2 degli Specification-Based Testing
    @Test
    void casoSemplice() {
        int[] array = {2,3};
        assertEquals(List.of(Optional.of(Pair.of(0, 1))), TwoSumProblem.twoSum(array,5));
    }
    @Test
    void casoComplesso() {
        int[] array = {1,4,2,9,8,3};
        assertEquals(List.of(Optional.of(Pair.of(1, 5))), TwoSumProblem.twoSum(array,7));
    }
    @Test
    void casoNumeriNegativi() {
        int[] array = {2,-3,1,8,4,9,-9};
        assertEquals(List.of(Optional.of(Pair.of(1, 6))), TwoSumProblem.twoSum(array,-12));
    }

    //metodo per la creazione di un array di oggetti Optional
    public static ArrayList<Optional> createArray(int[] coppie){
        ArrayList<Optional> arrayOfOptional = new ArrayList<>();
        for(int i=0; i<coppie.length; i += 2){
            arrayOfOptional.add(Optional.of(Pair.of(coppie[i], coppie[i+1])));
        }
        return  arrayOfOptional;
    }


    @ParameterizedTest
    @DisplayName("ValuesNulloEmptyRitornaNull")
    @NullAndEmptySource
    void valuesNulloEmptyRitornaNull(int[] values) {
        //test case ID: T1, T2
        assertNull(TwoSumProblem.twoSum(values, 0));
    }

    @Test
    @DisplayName("ValuesUnElementoRestituisceEmpty")
    void valuesUnElementoRestituisceEmpty() {
        //test case ID: T3
        Assertions.assertEquals(new ArrayList<>(), TwoSumProblem.twoSum(new int[]{5}, 10));
    }

    @ParameterizedTest
    @DisplayName("ValuesPiuElementiTargteMaggioreDiZero")
    @MethodSource("sourceTargetMaggioreDiZero")
    void valuesPiuElementiTargteMaggioreDiZero(int[] values, int target, ArrayList<Optional> expected) {
        assertEquals(expected, TwoSumProblem.twoSum(values, target));
    }

    static Stream<Arguments> sourceTargetMaggioreDiZero() {
        return Stream.of(
                //test case ID: T4
                arguments(new int[]{2, -5, 16, 4, -11}, 18, createArray(new int[]{0, 2})),
                //test case ID: T5
                arguments(new int[]{10, -23, 15, 2, -8}, 7, createArray(new int[]{2, 4})),
                //test case ID: T6
                arguments(new int[]{6, 5, -2, 5, -11}, 3, createArray(new int[]{1, 2, 2, 3}))
        );
    }

    @ParameterizedTest
    @MethodSource("generatorMinoreDiZero")
    @DisplayName("valuesPiuElementiTargetMinoreDiZero")
    void valuesPiuElementiTargetMinoreDiZero(final int[] values, final int target, final ArrayList<Optional<Pair<Integer, Integer>>> expected) {
        assertEquals(expected, TwoSumProblem.twoSum(values, target));
    }

    static Stream<Arguments> generatorMinoreDiZero() {
        return Stream.of(
                // test case ID: T7
                arguments(new int[]{-1, 12, -1, 80}, -2, createArray(new int[]{0,2})),
                // test case ID: T8
                arguments(new int[]{5, 40, -6, 5, 2}, -4, createArray(new int[]{2,4})),
                // test case ID: T9
                arguments(new int[]{2, -4, -30, -8, -6, 9, 24}, -6, createArray(new int[]{0,3,2,6}))
        );
    }

    @ParameterizedTest
    @MethodSource("generatorMaggioreDiZero")
    @DisplayName("valuesPiuElementiTargetUgualeDiZero")
    void valuesPiuElementiTargetUgualeDiZero(final int[] values, final int target, final ArrayList<Optional<Pair<Integer, Integer>>> expected) {
        assertEquals(expected, TwoSumProblem.twoSum(values, target));
    }

    static Stream<Arguments> generatorMaggioreDiZero() {
        return Stream.of(
                // test case ID: T10
                arguments(new int[]{-1, 4, 1, 7}, 0, createArray(new int[]{0,2})),
                // test case ID: T11
                arguments(new int[]{-2, 20, -2, -20, -6, 6, 1}, 0, createArray(new int[]{1,3,4,5}))
        );
    }

    @Test
    @DisplayName("valuesNonContieneCoppie")
    void valuesNonContieneCoppie() {
        // test case ID: T12
        assertEquals(new ArrayList<>(), TwoSumProblem.twoSum(new int[]{9, 40, -1, 28},10));
    }

    @Test
    @DisplayName("TargetContenutoNellArray")
    void targetContenutoNellArray() {
        //Test Case ID: T13
        assertEquals(createArray(new int[]{2,4}), TwoSumProblem.twoSum(new int[]{1, 12, 3, 4, 0}, 3));
    }

    @Test
    @DisplayName("tutteLeCoppieSonoSoluzioni")
    void tutteLeCoppieSonoSoluzioni() {
        //Test Case ID: T14
        assertEquals(createArray(new int[]{0, 1, 0, 2, 1, 2, 0, 3, 1, 3, 2, 3}), TwoSumProblem.twoSum(new int[]{2, 2, 2, 2}, 4));
    }

    @Test
    @DisplayName("piuCoppieConUnElementoInComune")
    void piuCoppieConUnElementoInComune() {
        //Test Case ID: T15
        assertEquals(createArray(new int[]{1, 2, 2, 3}), TwoSumProblem.twoSum(new int[]{5,1,3,1,6}, 4));
    }

}