# Bachelorarbeit - Onboarding von Programmieranfängern in professionellen Entwicklungsumgebungen

## Entwicklung eines Walkthroughs durch die IntelliJ IDE 
Das Onboarding wurde als Plugin für IntelliJ umgesetzt, dementsprechend befinden sich die Nutzer im Kontext der Entwicklungsumgebung, wenn sie das Tutorial starten. Außerdem lässt sich ein Plugin leicht an Studierende verteilen, da man in IntelliJ einfach Plugins von externen Quellen über einen Link laden kann.
Alternativ kann auch eine Plugin-Zip-Datei geladen und installiert werden. Um bestimmte Aspekte der IDE im Tutorial referenzieren zu können, basiert dieses Tutorial auf dem Starterpaket aus dem OOP-Kurs „A-Christmas-Game-starter“. Es wurde so ausgewählt, dass den Studierenden der Aufbau des Projekts bekannt
ist und es möglichst viele Aspekte enthält, die in Erklärungen referenziert werden können. Für weitere Tutorials können auch andere Starterpakete oder eigene Projekte gewählt werden.

## Aufbau des Tutorials
Der Inhalt des Tutorials wird einmalig über einen relativen Pfad aus einer JSON-Datei im Ressource-Ordner geladen. Der Vorteil dieser Art der Umsetzung ist die Wiederverwendbarkeit, Editierbarkeit und Erweiterbarkeit, da ein komplettes Tutorial in einer einzigen JSON-Datei enthalten ist. Mögliche weitere Tutorials können so nach dem Vorbild dieser Datei einfach angelegt und über einmalige Änderung des Pfads eingebunden werden. Ein Tutorial-JSON ist nach einer festen Struktur aufgebaut:
`{
 type: STRING (Titel des referenzierten Starter-Pakets),
 description: STRING (Information über Inhalt),
 author: STRING (Author des Tutorials),
 number_of_steps: INT,
 steps: ARRAY OF OBJECTS [
 {
 title: STRING (Wird angezeigt als Überschrift),
 content: STRING (Wird angezeigt als Text),
 pos: INT (Position; 0 bis number_of_steps-1),
 targets: ARRAY OF OBJECTS [
 {arrow: STRING ([UP | LEFT | NONE]),
  target-name: STRING (aus Tabelle 3)
  }
  ]
  },
  {
  …
  }
  ]
  }`
  Es gibt ein vordefiniertes Set mit Werten für das Feld target-name, die im Code erkannt und auf die jeweilige Position des UIs von IntelliJ gemappt werden. Dieses Set kann beliebig ergänzt werden, indem in PositionCalculator.java eine Berechnung der Highlightposition für den gewünschten target-name angestellt
wird. Das Hervorheben einiger ursprünglich geplanter Bereiche war nicht möglich. Zum Beispiel würde es sich im Tutorial an einigen Stellen anbieten, bestimmte Ordner in der Projetstruktur hervorzuheben. Deren Koordinaten sind allerdings nicht erreichbar. Auch die Dimensionen des PROJECT_DROPDOWN sind nicht auslesbar und daher nur angenähert. Dementsprechend können geringfügige Ungenauigkeiten im Highlighting nicht vollständig vermieden werden.

## Installation
Um das Plugin zu installieren und zu testen, muss die Zip-Datei intellij_walkthrough-1.0-SNAPSHOT.zip in IntelliJ unter Settings > Plugins > Install Plugin from Disk hinzugefügt werden. 