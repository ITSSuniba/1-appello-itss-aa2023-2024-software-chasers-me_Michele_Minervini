package org.example.Homework2;

import java.util.ArrayList;
import java.util.List;

public class LetterOrderChecker {
    public static boolean checkOrder(String str, char firstLetter, char secondLetter) {

        // controlla se la stringa è nulla, vuota o ha lunghezza 1
        if (str == null || str.isEmpty() || str.length() == 1) {
            return false;
        }

        // controlla se firstLetter è uguale a secondLetter
        if (firstLetter == secondLetter){
            return false;
        }

        // lista per contenere gli indici della prima lettera nella stringa
        List<Integer> indices = new ArrayList<>();
        // trova il primo indice della prima lettera nella stringa
        int firstIndex = str.indexOf(firstLetter);
        // Trova l'indice della seconda lettera nella stringa
        int secondIndex = str.indexOf(secondLetter);

        // Itera finché ci sono ulteriori occorrenze della prima lettera e le aggiunge a indices
        while (firstIndex != -1) {
            indices.add(firstIndex);
            firstIndex = str.indexOf(firstLetter, firstIndex + 1);
        }

        // Controlla se entrambe le lettere sono presenti nella stringa
        if (!indices.isEmpty() && secondIndex != -1) {
            // Controlla se tutte le occorrenze della prima lettera sono prima della seconda lettera
            return indices.stream().allMatch(index -> index < secondIndex);
        }

        // Se una delle due lettere non è presente, restituisce false
        return false;
    }

}
