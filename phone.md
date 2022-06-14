## Fonctionnent de l'api Apps

### Étape 1, register l'application
Pour commencer, il va falloir créer une classe dans `client.phone.apps` dans laquelle vous allez hériter de `app`. Il faut ensuite récupérer le constructeur dans lequel vous pourrez paramétrer la version de l'application, le nom et l'icône (`ResourceLocagion`).
Ensuite il vous faudra implémenter la fonction `OnClick(EntityPlayer) ` qui s'exécutera quand l'application sera exécutée, si vous laissez l'appel au super dans `OnClick(EntityPlayer) `, par défaut elle renvoie un message au joueur qui précise que l'application n'est pas opérationnelle. Mais vous pouvez par exemple ouvrir un gui au joueur. (voir suite) 

Maintenant que la classe est prête, vous pouvez l'enregistrer dans `Apps` ainsi : `public static App appExemple = new AppExemple();` Il faut évidemment remplacer appExemple par un nom de variable explicite et de même pour l'appel à votre classe. 

### Créer une interface utilisateur pour son application 

Pour faire cela, vous pourrez utiliser la classe PhoneFrame 
dans laquelle vous pourrez jouer avec un GUI de base (le contour et l'heure) 

Pour l'instant aucune info sur les fonctions, cette classe n'existe pas encore 

### La classe PhoneUtils
