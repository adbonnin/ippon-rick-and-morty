# ippon-rick-and-morty

## Environnement de développement

Le projet utilise la bibliothèque [Apollo](https://www.apollographql.com/) pour les échanger en GraphQL.

La commande suivante met à jour le schéma utilisé la lors de la génération :

```shell
./gradlew downloadApolloSchema \
  --endpoint="https://rickandmortyapi.com/graphql" \
  --schema="app/src/main/graphql/fr/adbonnin/rickandmorty/api/schema.json"
```