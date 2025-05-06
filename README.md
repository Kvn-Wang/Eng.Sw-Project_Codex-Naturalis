# Final Project Software Engineering Course - AA 2023-2024

## Codex Naturalis
Software implementation of the board game ***Codex Naturalis***. Rules available in italian on the
[official website](https://www.craniocreations.it/storage/media/product_downloads/126/1516/CODEX_ITA_Rules_compressed.pdf).
![alt text](https://github.com/Kvn-Wang/Eng.Sw-Project_Codex-Naturalis/blob/main/CodexNaturalis/src/main/resources/it/polimi/codexnaturalis/graphics/CODEX_Rulebook_IT/GameCover.png)


## Implemented Features
| Feature        |                    Implemented                    |
|:---------------|:-------------------------------------------------:|
| Basic rules    |                :heavy_check_mark:                 |
| Complete rules |                :heavy_check_mark:                 |
| Socket         |                :heavy_check_mark:                 |
| RMI            |                :heavy_check_mark:                 |
| TUI            |                :heavy_check_mark:                 |
| GUI            |                :heavy_check_mark:                 |
| Multiple games |                :heavy_check_mark:                 |
| Chat           |                :heavy_check_mark:                 |
| Persistence    |                        :x:                        |
| Disconnections |                        :x:                        |


## Componenti del gruppo
- [__Kevin Wang__](https://github.com/Kvn-Wang)
- [__Pietro Sacchi__](https://github.com/Piggotherock)
- [__Valerio Cipolloni__](https://github.com/Knacken8)
- [__Maria Sfondrini__](https://github.com/3Mari)


## How to run
- In the [Jar](https://github.com/Kvn-Wang/Eng.Sw-Project_Codex-Naturalis/tree/main/CodexNaturalis/out/artifacts) folder there are two jar files, one for the server and one for the client.

- The server can be run with the following command:
    ```shell
    java -jar CodexNaturalis.jar
    ```
  The RMI port is 1099 and the socket port is 6666.

- The client can be run with the following command:
    ```shell 
    java -jar CodexNaturalis.jar
    ```

## Disclaimers
We have tested the jar files on windows environment.

[_Codex Naturalis_](https://www.craniocreations.it/prodotto/codex-naturalis) is property of [_Cranio Creations_](https://www.craniocreations.it) and all of the copyrighted graphical assets used in this project were supplied by [_Politecnico di Milano_](https://www.polimi.it) in collaboration with their rights' holders.
