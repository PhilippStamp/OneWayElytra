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
<summary>language.yml</summary>


```
# #
# # OneWayElytra | language.yml
# #

prefix: '&5OneWayElytra &f» '
commandDenied: '&cYou are not allowed to execute this command!'
locationSet: '&7You set the location &asuccessfully&7.'
radiusSet: '&7You set the radius to &a%radius%&7.'
boostMultiplierSet: '&7You set the boostmultiplier to &a%multiplier%&7.'
wrongArgs: '&cYou tried to use wrong arguments. Please check &4/onewayelytra&c.'

# # boostmessage
primary_color: light_gray
secondary_color: yellow
# # please do not use colorcodes. The primary color will be used for the message and the secondary color will be used for the keybinding.
boostMessage: Press %keybinding% to get a boost.

```


</details>



