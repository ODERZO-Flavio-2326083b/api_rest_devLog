# Partie IHM

## 🚀 **Installation du Projet**

### 1. **Cloner le projet**
Clonez ce projet dans le répertoire souhaité de votre machine locale. Si vous utilisez Git, exécutez la commande suivante dans votre terminal :

```bash
git clone <URL-du-repository>
```

Cela téléchargera une copie du projet sur votre machine. N'oubliez pas de remplacer <URL-du-repository> par l'URL du repository Git.

### 2. **Installer composer**

Une fois que vous avez cloné le projet, vous devez installer les dépendances PHP via Composer. Pour cela, assurez-vous d'avoir installé Composer sur votre machine. Si ce n'est pas encore fait, vous pouvez l'installer en suivant les instructions de la documentation officielle de Composer : Installation de Composer.

Ensuite, dans votre terminal, accédez au dossier du projet cloné et exécutez la commande suivante pour installer les dépendances :

```bash
composer install
```

Cette commande va télécharger toutes les dépendances spécifiées dans le fichier composer.json et générer le fichier d'autoload nécessaire pour votre projet.

### 3. **Démarrer le serveur local**

Une fois les dépendances installées, vous pouvez démarrer un serveur local PHP pour exécuter l'application. Assurez-vous d'être dans le dossier racine du projet, puis lancez le serveur intégré PHP avec cette commande :

```bash
php -S localhost:8000 -t public
```

Cela démarre un serveur local à l'adresse http://localhost:8000. Le paramètre -t public spécifie que le répertoire public/ est le dossier racine de l'application, ce qui est important pour la gestion des ressources et des fichiers publics comme les images, le CSS, et le JavaScript.

### 4. **Accéder à l'application**

Dans votre navigateur, ouvrez l'adresse suivante :

```text
http://localhost:8000
```

Cela devrait afficher la vue d'accueil de votre application. 