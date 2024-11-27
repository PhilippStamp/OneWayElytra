# OneWayElytra
Do you wanna fly into the air? Even without ever entering the end? This plugin adds a one-way-elytra for your spawn. Players are able to fly into the air and boost once they are in the air.

### Features
- 🪽 fly into the air without an elytra
- 🚀 boost once while flying

### Commands
**/onewayelytra | /owe**
_Shows you all the commands._

**/onewayelytra set <location/radius/boost> [radius/boost]**
_Sets the center for the startarea. Set the radius for this area._

### Configurations

<details>
<summary>config.yml</summary>

```
##
## OneWayElytra | config.yml
##

## radius in blocks
radius: 12

boostMultiplier: 5

## location
location:
  world: world
  x: 0
  y: 300
  z: 0
```


</details>


<details>
<summary>language.yml | EN</summary>


```
##
## OneWayElytra | language.yml
##

prefix: '&5OWE &f» '
commandDenied: '&cYou are not allowed to execute this command!'
locationSet: '&7You set the location &asuccessfully&7.'
radiusSet: '&7You set the radius to &a%radius%&7.'
boostMultiplierSet: '&7You set the boostmultiplier to &a%multiplier%&7.'
wrongArgs: '&cYou tried to use wrong arguments. Please check &4/onewayelytra&c.'
boostMessage: '&7Click into the air to get a boost!'

```


</details>


<details>
<summary>language.yml | DE</summary>


```
##
## OneWayElytra | language.yml
##

prefix: '&5OWE &f» '
commandDenied: '&cDu darfst diesen Befehl nicht benutzen.'
locationSet: '&7Du hast die Position&a erfolgreich &7gesetzt.'
radiusSet: '&7Du hast den Radius auf &a%radius% Blöcke &7eingestellt.'
boostMultiplierSet: '&7Du hast den Boostmultiplikator auf &a%multiplier% &7gesetzt.'
wrongArgs: '&cDu hast einen Fehler bei der Eingabe gemacht. Benutze &4/onewayelytra&c.'
boostMessage: '&7Schlag in die Luft, um einen Schub zu erhalten.'

```


</details>



