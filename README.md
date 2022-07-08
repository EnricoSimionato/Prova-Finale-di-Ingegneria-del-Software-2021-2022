# ing-sw-2022-Vitali-Tacca-Simionato

<h2>Prova Finale di Ingegneria del Software
2021 - 2022</h2>

---

![alt text](https://github.com/MichaelVitali/ing-sw-2022-Vitali-Tacca-Simionato/blob/master/src/main/resources/images/eriantys_cover.jpg?raw=true)

<h3>Implementazione del gioco</h3>
Il progetto prevede l'implementazione del gioco da tavolo Eriantys.

Il progetto consiste nell’implementazione del gioco in modo che sia fruibile in un contesto distribuito. Il sistema è composto da un singolo server, esso è in grado di gestire partite multiple. I client (uno per giocatore) si collegano al server e vengono distribuiti tra le varie partite a seconda delle loro scelte riguardo alla tipologia di partita.

L'implementazione dell'intero progetto sfrutta il pattern MVC (Model-View-Controller).

Lo scambio di messaggi tramite la rete è stato gestito mediante socket.

<h3>Note iniziali</h3>
Il gioco è stato implementato in modo che sia pienamente utilizzabile su sistemi operativi Linux, questo a causa di alcune scelte implementative. Su sistemi operativi diversi non è garantita la perfetta fruibilità. Su Windows la GUI è utilizzabile con uno schermo sufficientemente ampio, mentre alcuni caratteri della CLI non vengono riconosciuti.

<h2>Documentazione</h2>

---

All'interno della cartella "deliverables" si trova la documentazione necessaria per comprendere quale è stata la logica di progettazione del gioco.

<h3>UML</h3>
Le seguenti cartelle contengono rispettivamente i diagrammi delle classi nella loro prima versione e in quella definitiva, la quale è rappresentativa del codice del gioco:

- [UML iniziali](https://github.com/MichaelVitali/ing-sw-2022-Vitali-Tacca-Simionato/tree/master/deliverables/initial%20UMLs) 
- [UML finali](https://github.com/MichaelVitali/ing-sw-2022-Vitali-Tacca-Simionato/tree/master/deliverables/final%20UMLs)

<h3>Protocollo di comunicazione</h3>
Per la comunicazione di rete sono stati usati i Socket di Java. I messaggi che vengono ricevuti lato client renderizzano (o stampano a video) le modifiche effettuate sul modello. I messaggi che vengono inviati dal client, e quindi ricevuti dal server, richiedono di effettuare una certa azione di setup, o di gioco.<br>
Per maggiori dettagli inerenti la comunicazione di rete consultare il seguente link: 

- [protocollo di comunicazione](https://github.com/MichaelVitali/ing-sw-2022-Vitali-Tacca-Simionato/blob/master/deliverables/communication%20protocol)

Il protocollo descritto nel file è stato leggermente modificato negli ultimi giorni in quanto si sono presentati dei problemi con il rendering relativo ai characters. Il codice definitivo non presenta la differenziazione tra i messaggi inviati dal server. Esso invia solo messaggi di tipo GameMessage, per questo la grafica è più lenta di quella che avremmo voluto implementare.

<h3>Test coverage</h3>
Sono stati scritti i test necessari ad avere una buona copertura dei metodi implementati nelle classi di modello e controller.

![alt text](https://github.com/MichaelVitali/ing-sw-2022-Vitali-Tacca-Simionato/blob/master/deliverables/test%20Coverage/report_coverage.jpg?raw=true)
<br><br><br><br><br>

<h3>JavaDoc</h3>
La JavaDoc, redatta in parallelo allo sviluppo del gioco, fornisce una descrizione della maggior parte dei metodi implementati. Essa segue le tecniche di documentazione di Java.

<h2>Funzionalità</h2>

---

<h3>Funzionalità sviluppate</h3>

Le funzionalità che sono state sviluppate sono quelle necessarie a ottenere la fascia di voto maggiore (se funzionanti). In particolare l'implementazione prevede:<br>

- Regole complete
- CLI
- GUI (implementata con la libreria JavaFx)
- Comunicazione mediante socket
- Implementazione di tutte le carte personaggio (1ª FA)
- Modalità 4 giocatori (2ª FA)
- Gestione di partite multiple da parte del server (3ª FA)


<h2>Informazioni building </h2>

---

Il progetto sfrutta Maven per automatizzare il processo di compilazione.
Grazie a tale strumento sono anche stati creati i jar. Per sviluppare il progetto e compilarlo è stata usata la versione 17 di Java.

<h3>Jars</h3>
I file jar generati sono due: uno per il client e uno per il server. Gli eseguibili si possono trovare a questi link:<br>

- [jars](https://github.com/MichaelVitali/ing-sw-2022-Vitali-Tacca-Simionato/tree/master/deliverables/jars)

<h2>Esecuzione</h2>

---

<h3>CLI</h3>
Per avviare il client di Eriantys e giocare da CLI, il comando, da digitare sul terminale, è:<br>

```
java -jar eriantys-client.jar cli server-ip-address [server-port-number]
```

<h3>GUI</h3>
Per avviare il client di Eriantys e giocare da GUI, il comando, da digitare sul terminale, è:<br>

```
java -jar eriantys-client.jar gui server-ip-address [server-port-number]
```
<h3>Server</h3>
Per avviare il server di Eriantys, il comando, da digitare sul terminale, è:<br>

```
java -jar eriantys-server.jar [server-port-number]
```

<h3>Parametri facoltativi</h3>
I parametri tra parentesi quadre sono facoltativi e permettono di cambiare porta su cui mettere in ascolto il server. Di default la porta è 50000. Se viene modificata la porta su client, o sul server, deve essere avviato anche l'altro componente con la porta diversa.

<h2>Componenti del gruppo</h2>

---

- Michael Vitali 10730463
- Manuel Tacca 10707009
- Enrico Simionato 10698193
