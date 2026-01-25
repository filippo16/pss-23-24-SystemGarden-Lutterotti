# Relazione SystemGarden

## Abstract
Questo documento è una relazione di meta-livelo che descrive tutti i passaggi logici, concettuali e strutturali dell'applicativo creato di nome "System-Garden".

# Analisi
## Requisiti

Il software, denominato **SystemGarden**, mira alla realizzazione di un sistema gestionale per impianti di irrigazione domestici. L'obiettivo principale è fornire all'utente uno strumento completo per monitorare e controllare l'irrigazione della propria area verde, integrando funzionalità di automazione.


### Requisiti funzionali

- L'utente deve poter creare e gestire **multiple aree verdi**, ciascuna associata ad una città/località.
- Ogni area verde può contenere **settori di irrigazione** (valvole), gestibili singolarmente con funzionalità di avvio/arresto manuale.
- Deve essere possibile **programmare l'irrigazione automatica** per ogni settore, specificando orario di inizio, durata e giorni della settimana.
- L'utente deve poter aggiungere **sensori** (umidità, temperatura) per monitorare le condizioni ambientali dell'area.
- Deve essere presente un **sistema di notifiche** per informare l'utente su eventi significativi di input.
- L'utente deve poter navigare e visionare le sue Aree-Verdi con un interfaccia semplice ed intuitiva. 

### Requisiti non funzionali

- L'applicazione deve essere semplice, senza la necessità di design avanzato.
- Il sistema deve permettere l'aggiunta di nuovi tipi di sensori.
- Il sistema deve differenziare l'area verde tra un giardino o gruppo di piante.

### Definizione dei termini

- **Area-Verde**: rappresenta un giardino o un gruppo di piante da irrigare. Ad ogni area sono associabili settori e sensori.
- **Settore**: zona di irrigazione controllata da una valvola, con possibilità di programmazione temporale.
- **Sensore**: dispositivo (simulato) che rileva dati ambientali come temperatura o umidità.
- **Schedule**: programmazione oraria per l'irrigazione automatica di un settore.
- **SmartAdvisor**: componente opzionale che analizza i dati e fornisce raccomandazioni sulla necessità di irrigazione.
- **Location**: componente che rappresenta la località dell'area verde, utilizzata per il calcolo del fuso orario.


# Analisi e modello del dominio

Il sistema SystemGarden si basa su un modello del dominio che riflette la struttura reale di un impianto di irrigazione. L'entità centrale è Manager che gestisce l'Area-Verde, che a sua volta rappresenta uno spazio fisico da irrigare. Ogni area può contenere: più Settori, che  possono essere attivati manualmente o automaticamente secondo una Programmazione (Schedule), più sensori, che permettono di monitorare le condizioni ambientali, e un SmartAdvisor, che analizza i dati meteo per dare dei consigli di irrigazione.

Inoltre per quanto riguarda alla programmazione oraria si farà riferimento al fuso orario di quell'Area-Verde, quindi ad una Location.


```mermaid
classDiagram

class Manager {
    
}
<<interface>> Manager

class GreenArea {

}
<<interface>> GreenArea

class Sector {
    +irrigate()
    +stop()
}
<<interface>> Sector

class Schedule {
    +getNextRunTime()
}
<<interface>> Schedule

class Sensor {
    +readData()
}
<<interface>> Sensor

class SmartAdvisor {
    +getAdvice()
}
<<interface>> SmartAdvisor

class Location {
    +getCity()
    +getLocalTime()
}
<<interface>> Location

Manager *-- "0..N" GreenArea
GreenArea *-- "0..N" Sector
GreenArea *-- "0..N" Sensor
GreenArea *-- "0..1" SmartAdvisor
GreenArea *-- Location
Sector *-- Schedule

```

### Principali sfide

- **Creare un'interfaccia semplice e intuitiva**: garantire all'utente un'esperienza chiara e immediata nella gestione dei sistemi.
- **Gestire correttamente le entità legate ai sistemi di irrigazione**: considerando anche località e fuso orario per la programmazione.
- **Sistema di messaggistica**: garantire un buona comprensione del messaggio.
- **Sistema di advisor**: garantire un consiglio efficace per l'utente.
- **Aggiornamento reattivo dei dati**: notificare la view in tempo reale quando i sensori rilevano nuovi valori (simulati) e per la ricezioni di un nuovo consiglio.


Purtroppo la parte delle telecamere non si è deciso di implementarla poiché si rischiava di implementare qualcosa che sarebbe risultato simile alle altre componenti se avessi usato un telecamera mock di esempio. Al suo posto però è subentrato lo SmartAdvisor, la parte opzionale, che è riuscito a dare, secondo me, un carico maggiore rispetto alle telecamere. Purtroppo la parte collegata al Meteo non è stata implementata poiché si è superato il monte ore.

# Design
## Architettura

L'architettura adottata segue le regole del pattern **MVC** (Model-View-Controller). In questo caso il modello si sviluppa partendo da `Manager` che funge da entry point per tutto lo stato applicativo del modello.

`GreenArea` è un'interfaccia che viene implementata da `GreenAreaImpl`. In questo modo si può astrarre dall'implementazione dell'Area-Verde e lavorare solo con il contratto d'uso definito, perciò in futuro si potrà implementare diverse versioni di GreenArea.

Sono state modellate diverse entità associate a GreenArea:
- **Sector**: rappresenta una zona irrigabile con propria valvola e programmazione
- **Schedule**: gestice la programmazione di avvio e spegnimento impianto automatico
- **Sensor**: dispositivo per la lettura di dati ambientali
- **SmartAdvisor**: analizza i dati dei sensori e fornisce consigli intelligenti sull'irrigazione
- **Location**: informazioni sulla località per il calcolo del fuso orario

Tutte le entità elencate implementano una propria interfaccia in modo che in futuro, per alcune di esse, si possano implementare versioni differenti.

L'interfaccia grafica viene gestita principalmente della View e dal `MainViewHandler`. Il `MainViewHandler` si occupa di gestire e mostrare le Aree-Verdi visivamente. Seguendo i principi del pattern MVC, la parte "View", a seguito di input dell'utente, contatterà il Controller che poi aggiornerà le varie informazioni, generate o modificate dal Model, tramite i metodi della View.

```mermaid
classDiagram

    class Controller {
        <<interface>>
    }

    class View {
        <<interface>>
        +show()
        +showToast()
    }

    class ViewImpl {
        - MainViewHandler mainHandler
    }
    

    class MainViewHandler {

    }

    class Manager {
        <<interface>>
        +createGreenArea() : GreenArea
        +removeGreenArea() : boolean
    }

    class ManagerImpl {
        - Map greenAreas
    }

    class GreenArea {
        <<interface>>
        +addSector() : Sector
        +addSensor() : boolean
        +checkSchedule() : boolean
        +irrigateSector() : Sector
    }

    class Sensor {
        <<interface>>
        +readData() : double
    }

    class Sector {
        <<interface>>
        +irrigate()
        +stop()
        +updateSchedule()
    }

    class Schedule {
        <<interface>>
        +shouldStartNow() : boolean
        +shouldStopNow() : boolean
        +getDuration() : int
    }

    class SmartAdvisor {
        <<interface>>
        +getAdvice() : IrrigationAdvice
    }

    class Location {
        <<interface>>
        +getCity(): String
        +getTimezone(): ZoneId
        +getLocalTime(): LocalTime
    }



    ManagerImpl --|> Manager
    
    Controller --> View
    Controller --> Manager
    ViewImpl --|> View


    ViewImpl *-- MainViewHandler

    ManagerImpl *-- "0..N" GreenArea
    GreenArea *-- "0..2" Sensor
    GreenArea *-- "0..N" Sector
    GreenArea *-- SmartAdvisor
    GreenArea *-- Location
    
    Sector *-- Schedule
```

## Design dettagliato

### Creazione dei sensori
Rappresentazione UML del pattern **Factory Method** per la creazione dei sensori.

```mermaid
classDiagram

    class Sensor {
        <<interface>>
        +getType(): SensorType
    }

    class AbstractSensor {
        <<abstract>>
    }
    
    class HumiditySensor {

    }

    class TemperatureSensor {

    }

    class SensorFactory {
        <<interface>>
        +createSensor(): Sensor
    }

    class SensorFactoryImpl {
        +createSensor(): Sensor
    }

    Sensor <|-- AbstractSensor
    AbstractSensor <|-- HumiditySensor
    AbstractSensor <|-- TemperatureSensor
    SensorFactory <|-- SensorFactoryImpl
    SensorFactory -- Sensor
```

#### Problema
Durante lo sviluppo ci sono stati dei problemi nella creazione delle istanze dei sensori, e inoltre
in fase di analisi si era riscontrata la possibilità di aggiungere nuove tipologie di sensori (es. sensore di pioggia, sensore di luce).

#### Soluzione
La soluzione adottata è stata quella di utilizzare il pattern **Factory Method**, in particolare la versione parametrizzata. L'interfaccia `SensorFactory` definisce il contratto d'uso della factory. La classe `SensorFactoryImpl` è responsabile della creazione dei sensori.
Questo pattern semplifica la creazione dei sensori, definendo una classe specifica per questo compito. Ogni tipo di sensore viene creato in base al parametro `type` passato alla factory.



### Notifica aggiornamento sensori
Rappresentazione UML del pattern **Observer** per la notifica dei dati sensori.

```mermaid
    classDiagram
        class SensorObservable {
        +addObserver( SensorObserver )
        +removeObserver( SensorObserver )
        +notifyObservers()
    }
    <<interface>> SensorObservable

    class SensorObserver {
        +onSensorUpdate()
    }
    <<interface>> SensorObserver

    class AbstractSensor {

    }

    class ViewImpl {

    }

    class GreenAreaImpl {

    }

    SensorObservable *-- SensorObserver
    SensorObservable <|-- AbstractSensor
    SensorObserver <|-- ViewImpl
    SensorObserver <|-- GreenAreaImpl
```

#### Problema
I problemi riscontrati sono stati quelli di: 
- Notificare alla view quando un sensore ha un nuovo valore. In questo modo la view può visualizzare le informazioni aggiornate per avvisare l'utente.
- Avvisare GreenArea di nuovi dati in modo che possa produrre un advice per l'utente, tramite SmartAdvisor.

#### Soluzione
La soluzione adottata è il pattern **Observer**: l'interfaccia `SensorObserver` definisce il metodo di callback `onSensorUpdate`. L'interfaccia `SensorObservable` definisce i metodi per gestire gli observer.
In questo scenario, `AbstractSensor` implementa la logica dell'observable come template e notifica alla `View` e  `GreenAreaImpl` (observer) quando ha un nuovo valore.

### Strategia per consigli irrigazione
Rappresentazione UML del pattern **Strategy** per i consigli di irrigazione.

```mermaid
classDiagram
    class AdvisorStrategy {
        <<interface>>
        +getAdvice(): IrrigationAdvice
    }

    class SensorAdvisor {
        +getAdvice(): IrrigationAdvice
    }

    class SmartAdvisorImpl {
        -AdvisorStrategy advisor
        +setStrategy( AdvisorStrategy )
    }

    AdvisorStrategy <|-- SensorAdvisor
    SmartAdvisorImpl *-- AdvisorStrategy
```

#### Problema
Il sistema deve analizzare i dati dei sensori (temperatura e umidità) per fornire consigli intelligenti sull'irrigazione. In futuro potrebbe essere necessario cambiare l'algoritmo di analisi (ad esempio basandosi su dati meteo invece che sui sensori locali).

#### Soluzione
La soluzione adottata è il pattern **Strategy**: l'interfaccia `AdvisorStrategy` definisce il contratto per gli algoritmi di analisi. `SmartAdvisorImpl` è il "context" che utilizza una strategia configurabile.
Attualmente l'implementazione `SensorAdvisor` analizza umidità e temperatura per determinare se è necessario irrigare. Il pattern permette di aggiungere nuove strategie (es. `WeatherAdvisor`) senza modificare il codice esistente.

Inoltre in futuro si potrebbe anche aggiungere la possbilità di scegliere in modo automatico quale della strategia (es. se non ho sensori utilizza `WeatherAdvisor`).


### Notifica consigli irrigazione
Rappresentazione UML del pattern **Observer** per la notifica dei consigli dell'Advisor.

```mermaid
classDiagram
    class AdvisorObservable {
        <<interface>>
        +addAdvisorObserver( AdvisorObserver )
        +removeAdvisorObserver( AdvisorObserver )
        +notifyAdvisorObservers()
    }

    class AdvisorObserver {
        <<interface>>
        +onAdviceReceived()
    }

    class GreenAreaImpl {

    }

    class ViewImpl {

    }

    AdvisorObservable <|-- GreenAreaImpl
    AdvisorObserver <|-- ViewImpl
    AdvisorObservable *-- AdvisorObserver
```

#### Problema
Quando lo `SmartAdvisor` genera un nuovo consiglio di irrigazione (basato sui dati dei sensori), la view deve essere notificata per mostrare il consiglio all'utente in tempo reale.

#### Soluzione
La soluzione adottata è nuovamente il pattern **Observer**: l'interfaccia `AdvisorObserver` definisce il metodo di callback `onAdviceReceived`. L'interfaccia `AdvisorObservable` definisce i metodi per gestire gli observer.
In questo scenario, `GreenAreaImpl` implementa `AdvisorObservable` e, quando riceve un aggiornamento dai sensori (`onSensorUpdate`), interroga lo `SmartAdvisor` per ottenere un consiglio e notifica tutti gli observer (in questo caso `ViewImpl`).


## Sviluppo
### Testing automatizzato
Il progetto è stato integrato con un sistema di testing completamente automatizzato grazie all'ausilio di JUnit.

#### Premessa
In alcuni i test, sono state utilizzate delle classi **mock** per rendere i test il più possibile isolati. In questo modo si evita l'utilizzo di codice esterno da quello della classe da testare.


#### Componenti sottoposti ai test
- **GreenAreaImplTest**: verifica la corretta gestione di settori, sensori, notifiche observer e programmazione dell'irrigazione.
- **ManagerImpl**: verifica la creazione e rimozione delle aree verdi, la gestione dei limiti massimi e il refresh dei dati dei sensori.
- **SectorImpl**: verifica il corretto funzionamento dell'irrigazione manuale, lo stato della valvola e la gestione della programmazione.
- **ScheduleImpl**: verifica il controllo degli orari di inizio e fine irrigazione, i giorni attivi e l'aggiornamento dei parametri.
- **LocationImpl**: verifica il corretto calcolo del fuso orario e dell'ora locale.
- **SensorFactoryImpl**: verifica la corretta creazione dei sensori (HumiditySensor e TemperatureSensor) in base al tipo richiesto.
- **SensorImpl**: verifica la lettura dei dati e la generazione dei valori simulati.
- **SensorObserver**: verifica il pattern Observer per i sensori, inclusa la registrazione, notifica e rimozione degli observer.
- **SmartAdvisorImpl**: verifica la corretta generazione dei consigli di irrigazione in base ai valori di temperatura e umidità.
- **SensorAdvisor**: verifica la logica della strategia di analisi dei dati ambientali.



## Note di sviluppo

### Utilizzo di Stream per filtrare collezioni
Dove: `it.unibo.systemgarden.model.impl.GreenAreaImpl`

```java
@Override
public Sector getSector( final String sectorId ) {
    return this.sectors.stream()
        .filter( s -> s.getId().equals( sectorId ) )
        .findFirst()
        .orElse( null );
}

```


#### Utilizzo di metodi Generci e Consumer per lambda function
Dove: `it.unibo.systemgarden.view.utils.DialogHelper`

```java
public static<R, C extends DialogController<R>> R showDialog( 
        final String fxmlPath, final String title, final String css, 
        final Consumer<C> controllerInit
    ) {
        
    try {

        final FXMLLoader loader = new FXMLLoader( ClassLoader.getSystemResource( fxmlPath ) );
        final Parent root = loader.load();

        final Stage dialog = createDialogStage( title, root, css );
        final C controller = loader.getController();
        controller.setStage( dialog );

        
        if ( controllerInit != null ) {
            controllerInit.accept( controller );
        }

        dialog.showAndWait();

        return controller.getResult();
    } catch (Exception e) {
        throw new RuntimeException( "Error showing dialog: " + e.getMessage(), e );
    }
}
```

### Utilizzo di ScheduledExecutorService per task periodici
Dove: `it.unibo.systemgarden.controller.impl.ControllerImpl`

```java
@Override
public void start() {
    scheduler = Executors.newSingleThreadScheduledExecutor();
    
    long now = System.currentTimeMillis();
    long delayToNextMinute = 60000 - ( now % 60000 );
    
    scheduler.scheduleAtFixedRate(() -> {
        checkAllSchedules();  
        updateClocks();       
        model.refreshSensorData();  
    }, delayToNextMinute, 60000, TimeUnit.MILLISECONDS);
    
    view.show();
}
```


#### Utilizzo di enumerazioni
Dove: `it.unibo.systemgarden.model.utils.SensorType`
```java
public enum SensorType {
    TEMPERATURE("temperature"),
    HUMIDITY("humidity");

    private final String label;

    SensorType(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String getUnit() {
        switch (this) {
            case TEMPERATURE:
                return "°C";
            case HUMIDITY:
                return "%";
            default:
                return "";
        }
    }

    @Override
    public String toString() {
        return label.substring( 0, 1 ).toUpperCase() + label.substring( 1 );
    }
}
```

### Utilizzo di Platform.runLater per thread-safety JavaFX
Dove: `it.unibo.systemgarden.view.impl.ViewImpl`

```java
@Override
public void onSensorUpdate( final String areaId, final String sensorId, 
    final double newValue, final SensorType type 
) {
    Platform.runLater(() -> mainHandler.refreshSensorData( areaId, sensorId, newValue ));
}
```

## Commenti finali

### Autovalutazione

**Punti di forza:**
- Buona fase di analisi e progettazione.
- Applicazione di diversi design pattern (Factory Method, Strategy, Observer).
- Isolamento dei componenti tramite interfacce.
- Buona suddivizione del codice lato view, con utilizzo di controller, fxml e metodi generici.


**Punti di debolezza:**
- Mancanza di persistenza dei dati.
- Non tutte le classi rispettano il single responsability principle.
- Per i metodi crud creare un unico metodo di interfacciamento, magari basato su un type Action.
- Migliorabile l'utilizzo dell'observer pattern lato Advisor, magari implementarne uno generico per modifica di settori, sensori e advice. (GreenAreaObservable)


### Difficoltà riscontrate

- **Modellazione**: è stato complesso comprendere quale sia la migliore modellazione da adottare per un aspetto specifico del dominio analisi. All’inizio è stato complesso mantenere una netta separazione tra progettazione e sviluppo, avevo la tendenza ad anticipare decisioni implementative.
- **Gestione dell'entità**: gestire in modo corretto le relazioni tra le entità del dominio.
- **Mancanza di un secondo parere**: la mancanza di un secondo parere di un collega non mi ha fatto aiutato nella costruzione generale e mi è mancata.


### Guida utente
Per utilizzare l'applicativo:
1. Creare una nuova Area Verde (pulsante in alto a destra) specificando nome e città (es. New York, Cesena, Arco).
2. Aggiungere settori all'area per definire le zone irrigabili, tramite l'apposito bottone.
3. Aggiungere sensori per monitorare temperatura e umidità, tramite l'apposito bottone.
4. Configurare la programmazione per ogni settore, tramite il pulsante "Orari" verrà aperto un dialog di configurazione.
5. Utilizzare i pulsanti Play/Stop per l'irrigazione manuale, tramite i pulsanti "Avvia" "Ferma".
6. Visualizzare i consigli dello SmartAdvisor, previa inserimento dei due sensori di Temperatura e Umidità.

All'avvio verrà creata una Area-Verde di esempio.