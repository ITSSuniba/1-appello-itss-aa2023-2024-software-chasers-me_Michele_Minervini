package org.example.Homework1;

import java.util.HashMap;
import java.util.Optional;
import org.apache.commons.lang3.tuple.Pair;
import java.util.ArrayList;

public final class TwoSumProblem {
    private TwoSumProblem() {
    }
    public static ArrayList<Optional> twoSum(final int[] values, final int target) {
        // Mappa che tiene traccia degli indici associati ai valori.
        HashMap<Integer, ArrayList<Integer>> valueToIndices = new HashMap<>();
        // Lista risultante contenente le coppie di indici.
        ArrayList<Optional> result = new ArrayList<>();
        // Se l'array è nullo o vuoto, restituisci null.
        if (values == null || values.length == 0) {
            return null;
        }
        // Itera su ogni elemento dell'array.
        for (int i = 0; i < values.length; i++) {
            // Calcola il restante (rem) necessario per raggiungere il target.
            final var rem = target - values[i];
            // Se il rem è presente nella mappa degli indici, aggiungi tutte le coppie possibili.
            if (valueToIndices.containsKey(rem)) {
                // Iterazione necessaria per identificare tutte le coppie.
                for (int index : valueToIndices.get(rem)) {
                    result.add(Optional.of(Pair.of(index, i)));
                }
            }
            // Se il valore corrente non è presente nella mappa, aggiungi l'indice corrente.
            if (!valueToIndices.containsKey(values[i])) {
                valueToIndices.put(values[i], new ArrayList<>());
            }
            valueToIndices.get(values[i]).add(i);
        }
        return result;
    }
}
