# App Architecture

<a href="https://docs.google.com/document/d/1SHVdZoCNXS4wXgZLo9SwFIShDv7H1xATdIcbCwEF7AE/edit" title="Google Drive Folder">
App Features Discussion </a>
<br>
<a href="https://www.figma.com/proto/Lf8xJDSLl2WVLb3frxfqZE/50.001-1D-Wireframe?node-id=1:2&viewport=286,312,0.5&scaling=scale-down">
App Designs (Figma)</a>

App Features to be added

## Commit<br>
### dagger skeleton<br>
dependency injection framework

2 classes: auth + main

Packages<br>
### di<br>

*auth*
> AuthScope - scopes AuthViewModules + AuthModule

*main*

> ActivityBuildersModule - building activities<br>
> AppComponent - contains all the modules to be injected + binding instance of application at runtime for use in BaseApplication<br>
> AppModule - contains FirebaseDatabase instance

### models

### ui

*auth*

> AuthActivity - Auth codes

*main*

### util

### viewmodels


### Package
> BaseApplication - inject application component into BaseApplication, used to keep track of auth status of user
