package it.corona.fabrick.constant;

public class BeanScope {

    private BeanScope() {
        super();
    }

    public static final String SINGLETON = "singleton";     // Una sola istanza per l'intera applicazione
    public static final String PROTOTYPE = "prototype";     // Una nuova istanza ogni volta che viene richiesto
    public static final String REQUEST = "request";         // Una nuova istanza per ogni HTTP request
    public static final String SESSION = "session";         // Una nuova istanza per ogni sessione utente
    public static final String APPLICATION = "application"; // Una nuova istanza per l'intera applicazione (simile a singleton)
}