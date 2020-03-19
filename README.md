# Bachelorarbeit - Onboarding von Programmieranfängern in professionellen Entwicklungsumgebungen

## Entwicklung eines Walkthroughs durch die IntelliJ IDE 
Das Plugin ergänzt die IDE um ein ToolWindow, in dem sich Nutzende durch einen grundlegenden Walkthrough klicken können. 
Die im Walkthrough beschriebenen Funktionen sind an die Anforderungen des OOP-Kurses der Medieninformatik an der Uni Regensburg angepasst.

Beim Starten des Plugins wird neben dem ToolWindow ein Overlay erstellt, das die selben Abmessungen hat wie die Entwicklungsumgebung.
Über dieses Overlay werden die Bereiche der IDE hervorgehoben/markiert, die gerade im ToolWindow erklärt werden.

## Inhalte
Die einzelnen Schritte mit textuellen Erklärungen sind als eigene HTML-Files angelegt, sodass der Walkthrough leicht geändert
oder angepasst werden kann (src/main/resources/browser/res/content). 

Die Navigation innerhalb des ToolWindows läuft über Javascript. 
