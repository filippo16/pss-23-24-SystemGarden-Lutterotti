# Dev Log

## 02.01.2026 – 06.01.2026 (ca. 12 ORE)
**Attività**
- Progettazione generale del sistema: analisi del dominio e design architetturale.

**Aspetti positivi**
- La fase di progettazione si è rivelata molto utile, permettendomi di comprendere meglio l’intero sistema e di affrontare lo sviluppo in modo più organizzato e consapevole.
- L’utilizzo dei diagrammi UML ha migliorato la leggibilità del progetto: una volta completata una parte, è stato possibile verificarne la coreenza ed è stato più facile apportare delle successive modifiche.

**Difficoltà**
- All’inizio è stato complesso mantenere una netta separazione tra progettazione e sviluppo, con la tendenza ad anticipare decisioni implementative.
- Durante la fase di design ho avuto difficoltà a distinguere la produzione software dal ciclo completo di un’applicazione in un contesto aziendale. In particolare, tendevo a includere anche aspetti legati alla realizzazione fisica dell’impianto di irrigazione (valvole, pompa) e alla parte HMI, rendendo meno chiaro il confine tra sistema software e sistema fisico.


## 09.12.2026 – 13.12.2026 (ca. 12 ORE)
**Attività**
- Creazione repository GitHub.
- Rifinitura progettazione. 
- Inizializzazione del progetto: creazione della struttura mvc e delle prime interfacce e logiche.

**Aspetti generali**
La progettazione mi ha permesso di avere un'idea chiara fin dall'inizio, ho avuto comnuque qualche piccola difficoltà a capire come procedere con la struttura del progetto. 


## 14.01.2026 – 17.01.2026 (ca. 23 ORE) forse meno
**Attività**
- Sviluppo della view mediante utilizzo di FXML e JavaFX.
- Sviluppo schedule con controllo del fuso orario

**Aspetti positivi**
- Nonostante alcune difficoltà iniziale, lo sviluppo e integrazione degli fxml nel progetto mi ha permesso di suddividere e leggere al meglio il codice scritto. 
- La separazione dei Controller dei FXML (Dialog in questo caso) che venivano generati tramite l'Helper Generale secondo me hanno dato una maggiore leggibilità al codice.

**Difficoltà**
- All'inizio ho avuto difficoltà a capire come procedere con la struttura della view, nonostante lo studio della documentazione.


## 18.01.2026 – 19.01.2026 (ca. 5 ORE)
**Attvità**
- Sistemare la partenza automatica dell'impianto (check tramite threadPool - vedere se esitono cronJob).
- Implementare Card Component con view fxml.


## 20.01.2026 - 21.01.2026 (ca. 5 ORE)
**Attività**
- Aggiunto un manager di tutte le aree verdi (migliore separazione dei concetti e così il controller non conosce tutte le greenaree)
- Aggiunto clock interno con visualizzazione.
- Aggiunti metodi crud per sector (come per green area).

## 21.01.2026 - xx.01.2026 (ca. 11 ORE)
**Attività**
- Implementazione pattern observer per i sensori
- Implementazione pattern facotry per la creazione dei sensori

**Difficoltà**
- Non c'è ne sono state troppe, più che altro mi chiedo se view può effettivamente essere un observer e le varie metodologie per iscriverla in modo più pulito.

9