## Fonctionnent de l'api Apps

### Étape 1, register l'application
Pour commencer, il va falloir créer une classe dans `client.phone.apps` dans laquelle vous allez hériter de `app`. Il faut ensuite récupérer le constructeur dans lequel vous pourrez paramétrer la version de l'application, le nom et l'icône (`ResourceLocagion`).
Ensuite il vous faudra implémenter la fonction `OnClick(EntityPlayer) ` qui s'exécutera quand l'application sera exécutée, si vous laissez l'appel au super dans `OnClick(EntityPlayer) `, par défaut elle renvoie un message au joueur qui précise que l'application n'est pas opérationnelle. Mais vous pouvez par exemple ouvrir un gui au joueur. (voir suite) 

Maintenant que la classe est prête, vous pouvez l'enregistrer dans `Apps` ainsi : `public static App appExemple = new AppExemple();` Il faut évidemment remplacer appExemple par un nom de variable explicite et de même pour l'appel à votre classe. 

### Créer une interface utilisateur pour son application 

Pour faire cela, vous pourrez utiliser la classe PhoneBaseFrame 
dans laquelle vous pourrez jouer avec un GUI de base (le contour et l'heure) 

Fonctions :
| Fonction | usage  | utilité  |
| :------------------:   | :-: | :-: |
| openGui() | PhoneBaseFrame#openGui() | Permet d'ouvrir un nouveau GUI Vierge qui contiendra donc uniquement la frame du téléphone et les quelques boutons. |
| addElementToGui() | PhoneBaseFrame#addElementToGui(GuiElement) | Permet d'ajouter tout type d'élément au GUI ouvert auparavant. Pour définir du texte ou autre, déclarer l'élement du gui en tant que variable et par exemple changer le texte d'un GuiTextField et ensuite l'ajouter. |


Pour l'instant aucune info sur les fonctions, cette classe n'existe pas encore 

### La classe PhoneUtils

Elle permet d'effectuer différentes actions utile avec le téléphone tel que : 

getInstalledApps() : Qui permet de récupérer de récupérer les applications installées.
*goHome()* : Qui permet au téléphone de retourner sur l'écran de séléction des applications.
