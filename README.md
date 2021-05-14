# SernoxCraft-SchotterSystem

Das SernoxCraft-SchoteerSystem ist ein professionelles und vollmodulares Money-System.
Die Spieler habe durch das Plugin die Möglichkeit, mit einer auswäglbaren Ingamewährung Ingamekäufe abzuschließen. 
Dabei wird auf grund der Perforance auf eine Datenbankverbindung gestzt welche in der "MySQL.yml" konfiguriertt werdenkann gesetzt.
Das SchotterSystem verfügt desweiteren über ein LogSystem, welche alle Transaktionen per Webhook auf einen Discord-Server protokolliert.
Prefix, Währung  und Discord-Webhook-URL sind in der "config.yml" einstellbar. Weitere Infos unten

## Spieler-Befehle

```
Command                                   | Permission              | Funktion

/balance                                  | -------------           | Zeigt dir deinen aktuellen Kontostand
/pay <player> <ammount>                   | -------------           | Überwist einem anderen Spiler Geld
```


## Admin-Befehle

```
Command                                   | Permission              | Funktion

/balance <player>                         | ss.balance.see.others   | Zeigt dir den Kontostand eines anderen Spielers
/addmoney <player> <ammount>              | ss.addmoney             | Fügt einem Spieler Geld zu seinem aktuellen Konto hinzu
/setmoney <player> <ammount>              | ss.addmoney             | Ersetzt das Geld auf dem Konto eines Spielers
```


## Konfigurationshilfe

### config.yml
```
// Startbalance ist die Summe an Geld, welche jeder User erhält, sobalt er sich zum ersten mal auf den CityBuild verbindet.
// Currency stellt die frei wählbare Währung da
// DiscordWebHookURL ist die URL die Ihr eingeben müsst um den Log zum laufen zu bekommen PFLICHT!

Prefix: '§cSchotterSystem §8➛§r '
StartBalance: 1000
Currency: Schotter
DiscordWebHookURL: https://discord.com/api/webhooks/64894984984985/12ur89013j89f809321809hf89h2894h8fh3w8

```

### MySQL.yml

```
// use MUSS auf true gestzt werden

mysql:
  use: true
  host: 0.0.0.0
  port: 3306
  user: <user>
  datenbase: <database>
  passwort: ******
  
```

Informationsvideo zu den ConfigFiles: [Klicke hier](https://youtu.be/TqrZ_ag3JTE)

# Viel Spaß wünschen
## LvckyAPI & IloveKOHL | Developer

