# App Architecture

<a href="https://docs.google.com/document/d/1SHVdZoCNXS4wXgZLo9SwFIShDv7H1xATdIcbCwEF7AE/edit" title="Google Drive Folder">
App Features Discussion </a>
<br>
<a href="https://www.figma.com/proto/Lf8xJDSLl2WVLb3frxfqZE/50.001-1D-Wireframe?node-id=1:2&viewport=286,312,0.5&scaling=scale-down">
App Designs (Figma)</a>

App Features to be added

## Commit<br>
dependency injection framework

2 activities: AuthActivity + MainActivity

Packages<br>
### di<br>

*data*
> UserDao
> * Room database methods for mods to User Model
>
> UserDatabase
> * Creates a singleton instance of Room database
> * Populates user (default = "-1")
>
> UserRepository
> * Wrapper class for User methods (insert, update, delete)
> * Creation of separate background threads to manage user data
> * returns User LiveData for observation

*auth*
> AuthFragmentBuildersModule
> * Build the dagger modules for GetStartedFragment, LoginFragment, RegisterFragment
>
> AuthScope
> * Scopes the AuthFragmentBuildersModule.class, AuthViewModelModule.class to Auth
>
> AuthViewModelModule
> * Binding UserViewModel in AuthViewModelModule, used in AuthActivity

*main*

> ActivityBuildersModule
> * Build the dagger modules for AuthActivity, MainActivity
>
> AppComponent
> * declares which modules to be injected + binding instance of application at runtime for use in BaseApplication
> * SessionManager exists in Application scope
>
> AppModule
> * contains the instances to be injected into classes, activities, fragments
>
> ViewModelFactoryModule
> * binding ViewModelProviderFactory, integrates ViewModels in the app
>
> ViewModelKey
> * Dagger multibindings using annotations

### models

> User
> * Creating a Room database on top of User model and integrates with gson serialization

### ui

*auth*

> AuthActivity
> * Login method: init intent to change to MainActivity.class
>
> GetStartedFragment<br>
> * Landing page for new users
> * Injects Logo into layout
> * Navigates to LoginFragment
>
> LoginFragment<br>
> * Login page, observes user, and auto-fills username (TODO: Remember Me Function)
> * Navigates to RegisterFragment upon press of signUp
> * Login details passes it to UserViewModel
>
> RegisterFragment<br>
> * New user registration page, user creates a new account
>
> UserViewModel
> * Contains the register, login methods
> * Connects to the SessionManager, get authenticates the user
> * Retrieves User LiveData

*main*

> MainActivity
> * Inflate the menu
> * Logout method: init intent to change to AuthActivity.class

### util

### viewmodels

> ViewModelProviderFactory
> * Work around to integrate ViewModel together with Dagger Android framework using Map<K, V>
> * Key refers to any class that extends ViewModel
> * Value refers to any providers of the ViewModel, Dagger Framework @provides auto generates the provide method for the
> injection of ViewModel instance in any module


### Package
> BaseApplication
> * inject application component into BaseApplication
>
> SessionManager
> * used to keep track of auth status of user (register, login, logout, user instance)


### Things to Work On
> 1. Material design styling of the text: typography, color
> 2. Purchase/Drawing of background image, injecting directly into ShapeableImageView (cropping/scaling may be required)
> 3. Shifting of user menu to the bottom, custom the design/utilize the menu<br>
> <a href="https://www.youtube.com/watch?v=IfgV9WzYOn8">Bottom App Bar</a><br>
> <a href="https://www.youtube.com/watch?v=tPV8xA7m-iw">Bottom Navigation View</a>