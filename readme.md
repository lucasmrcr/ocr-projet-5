# Installation d'un serveur mysql
- Installez un serveur mysql avec https://dev.mysql.com/downloads/installer/
- Créez une base de données
- Importez grâce à un client mysql ou en ligne de commande (mysql -u <utilisateur> -p -D <base_de_donnees> < ressources/sql/script.sql) le script sql initialisant la base de données

# Configurez le backend
Dans le fichier src/main/resources/application.properties, modifiez les valeurs concernant la base de données pour y inserer :
- Le nom de votre base de données
- Le nom d'utilisateur mysql
- Le mot de passe mysql

# Lancez les tests
Lancez les tests sur le backend avec la commande : `mvn clean test`
Lancez les tests sur le frontend avec les commandes : `npm run test` pour jest et `npm run e2e`pour cypress

# Lancer le projet
Lancez le backend avec la commande `mvn spring:boot run`
Lancez le frontend avec la command `npm start`
