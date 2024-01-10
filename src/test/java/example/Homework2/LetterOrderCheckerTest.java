package example.Homework2;

import net.jqwik.api.*;
import net.jqwik.api.constraints.CharRange;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.api.statistics.Histogram;
import net.jqwik.api.statistics.Statistics;
import net.jqwik.api.statistics.StatisticsReport;
import org.example.Homework2.LetterOrderChecker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LetterOrderCheckerTest {

    private final char firstLetter = 'a';
    private final char secondLetter = 'z';

    // PASS: tutte le occorenze di firstLetter sono posizionate prima di secondLetter
    @Property
    @Report(Reporting.GENERATED)
    @StatisticsReport(format = Histogram.class)
    void passCase(
            @ForAll @StringLength(min = 2, max = 15) @CharRange(from = 'b', to = 'y') String string) {

        Random random = new Random();
        int indice = random.nextInt(1, string.length());

        // Genera indici per il posizionamento di firstLetter e secondLetter
        List<Integer> indicesFirstLetter = generateIndices(0, indice - 1, indice);
        List<Integer> indicesSecondLetter = generateIndices(
                indice, string.length() - 1, string.length() - indice);

        // Modifica la stringa in base agli indici generati posizionando firstLetter e secondLetter
        StringBuilder modifiedString = new StringBuilder(string);
        indicesFirstLetter.forEach(i -> modifiedString.setCharAt(i, firstLetter));
        indicesSecondLetter.forEach(i -> modifiedString.setCharAt(i, secondLetter));

        // Verifica che l'ordine sia rispettato
        assertTrue(LetterOrderChecker.checkOrder(modifiedString.toString(), firstLetter, secondLetter));

        Statistics.label("Lunghezza").collect(string.length());
        Statistics.label("Indice").collect(indice);
        Statistics.label("Numero di A").collect(indicesFirstLetter.size());
        Statistics.label("Numero di z").collect(indicesSecondLetter.size());
    }

    // FAIL: almeno un occorenza di secondLetter è posizionata prima di secondLetter
    @Property
    @Report(Reporting.GENERATED)
    @StatisticsReport(format = Histogram.class)
    void failCase(
            @ForAll @StringLength(min = 2, max = 15) @CharRange(from = 'b', to = 'y') String string) {

        Random random = new Random();
        int numeroA = 1;
        int numeroZ = 1;

        // Modifica la stringa in base agli indici generati posizionando firstLetter e secondLetter
        StringBuilder modifiedString = new StringBuilder(string);
        if(string.length() > 2){
            // Genera indici per il posizionamento di firstLetter e secondLetter:
            List<Integer> indices = generateIndices(
                    1, string.length() - 2, string.length() - 2);
            for(int i = 0; i < indices.size(); i++){
                if(random.nextBoolean()){
                    modifiedString.setCharAt(i, firstLetter);
                    numeroA++;
                }
                else{
                    modifiedString.setCharAt(i, secondLetter);
                    numeroZ++;
                }
            }
            Statistics.label("Indici").collect(indices.size());
        }

        //Modifica della stringa inserendo firstletter in ultima posizione e secondletter in prima posizione
        modifiedString.setCharAt(0, secondLetter);
        modifiedString.setCharAt(string.length() - 1, firstLetter);
        // Verifica che l'ordine non è rispettato
        assertFalse(LetterOrderChecker.checkOrder(modifiedString.toString(), firstLetter, secondLetter));
        Statistics.label("Lunghezza").collect(string.length());
        Statistics.label("Numero di A").collect(numeroA);
        Statistics.label("Numero di Z").collect(numeroZ);
        Statistics.label("Numero di Z prima di A").
                collect(countLetterBeforeAnother(modifiedString, firstLetter, secondLetter));
    }


    // INVALID: la stringa è vuota o null, ha lunghezza pari a 1 o non contiene firstLetter o secondLetter
    @Property
    @Report(Reporting.GENERATED)
    void invalidCase(
            @ForAll @StringLength(max = 15) @CharRange(from = 'a', to = 'z') String string) {

        // Verifica se la stringa è vuota, ha lunghezza pari a 1 o non contiene firstLetter o secondLetter
        if (string.isEmpty() || string.length() == 1 ||
                !string.contains(String.valueOf(firstLetter)) || !string.contains(String.valueOf(secondLetter))) {
            assertFalse(LetterOrderChecker.checkOrder(string, firstLetter, secondLetter));
        }

        Statistics.label("Vuota").collect(string.isEmpty());
        Statistics.label("Lunghezza 1").collect(string.length() == 1);
        Statistics.label("Contiene first e second letter").collect(string.contains(String.valueOf(firstLetter)) && string.contains(String.valueOf(secondLetter)));
        Statistics.label("Risultato").collect(LetterOrderChecker.checkOrder(string, firstLetter,secondLetter));
    }

    // caso particolare in cui la stringa è null
    @ParameterizedTest
    @DisplayName("Stringa null")
    @NullSource
    void stringaNull(String str) {
        assertFalse(LetterOrderChecker.checkOrder(str,firstLetter,secondLetter));
    }

    // caso particolare in cui firstLetter è uguale a secondLetter
    @Test
    @DisplayName("firstLetter uguale a secondLetter")
    void firstLetterUgualeASecondLetter() {
        assertFalse(LetterOrderChecker.checkOrder("abcdef", 'a','a'));
    }

    // caso particolare in cui la stringa contiene numeri o caratteri speciali
    @Test
    @DisplayName("str contiene numeri o caratteri speciali")
    void strContieneNumeriOCaratteriSpeciali() {
        assertTrue(LetterOrderChecker.checkOrder("a!bc de1f0#@", 'a','f'));
    }

    // metodo per generare indici random
    private List<Integer> generateIndices(int startIndex, int endIndex, int size) {
        return Arbitraries.integers().between(startIndex, endIndex).list().ofMaxSize(size).ofMinSize(1).sample();
    }

    private int countLetterBeforeAnother(StringBuilder string, char letter1, char letter2){
        int count = 0;
        int i = 0;

        while (string.charAt(i) != letter1){
            if(string.charAt(i) == letter2){
                count++;
            }
            i++;
        }
        return  count;
    }
}


