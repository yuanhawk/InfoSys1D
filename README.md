# App Package Outline

[![Generic badge](https://img.shields.io/badge/PUG-1.0-<COLOR>.svg)](https://shields.io/)
[![MIT license](https://img.shields.io/badge/License-MIT-blue.svg)](https://lbesson.mit-license.org/)
[![Generic badge](https://img.shields.io/badge/SupportUsOn:-PlayStore-9cf.svg)](https://play.google.com/apps/testing/tech.sutd.pickupgame)

<a href="https://miro.com/app/board/o9J_lddUdVk=/" title="Design Patterns (Miro)">
Design Patterns (Miro) </a>
<br>
<a href="https://www.figma.com/proto/Lf8xJDSLl2WVLb3frxfqZE/50.001-1D-Wireframe?node-id=1:2&viewport=286,312,0.5&scaling=scale-down">
App Designs (Figma)</a>

## Commit<br>
2 activities: AuthActivity + MainActivity<br>
10 fragments: GetStartedFragment, LoginFragment, RegisterFragment, ResetFragment<br>
BookingFragment, NewActFragment, UpcomingActFragment, MainFragment<br>
EditProfileFragment, UserFragment

Packages<br>
### tech.sutd.pickupgame<br>
#### constant
> ClickState

#### data
> *ui*
> > *helper*
> > * Various database helper methods
> >
> > *new_activity*<br>
> > *past_activity*<br>
> > *upcoming_activity*<br>
> > *user*<br>
> > *your_activity*
> > * room database codes
> 
> > *worker*
> > * All the worker tasks for various databases
>
> > *AppDataManager*
> > * Concrete class that implements the AppDataManager methods
> > 
> > *AppExecutors*
> > * Custom executor codes
> >
> > *AppSchedulerProvider*
> > * Concrete class that stores custom RxJava Scheduler Threads
> >
> > *DataManager*
> > * Extends all methods from other databases using interfaces
> 
> > *Resource*
> > * RxJava enums & base observer setters
> 
> > *SchedulerProvider*
> > * Methods to retrieve RxJava Scheduler Threads

#### di
> Refer to Design Patterns (Miro)

#### models
> *ui*<br>
> >
> > BookingActivity<br>
> > * Data class for pushing to firebase realtime db + firestore
> >
> > NewActivity<br>
> > PastActivity<br>
> > UpcomingActivity<br>
> > YourActivity<br>
> > * Data class for inserting, updating, deleting, custom querying in Room
> 
> User<br>
> * Data class for inserting, updating, deleting, custom querying in Room
> 
> UserProfile
> * Data class for reading from and writing to firebase realtime db

### ui

> *auth*
> > *gettingstarted*<br>
> > GetStartedFragment
>
> > *login*<br>
> > LoginFragment
>
> > *register*<br>
> > RegisterFragment
>
> > *reset*<br>
> > ResetFragment
>
> > *viewmodel*<br>
> > UserViewModel
> > * firebase realtime db + firebaseAuth writes and reads: register, reset, updateUserDetail, login, insertUserDb
> > * room methods: insert, deleteAllUsers, getUsers
> 
> AuthActivity
> 
> *main*
> > *booking*<br>
> > BookingFragment<br>
> >
> > *main*
> > > *adapter*<br>
> > > FilterAdapter (ListAdapter) <br>
> > > * NewActivity db filter
> > >
> > > NewActivityAdapter (PagedListAdapter) <br>
> > > * Coupled to NewActFragment + MainFragment
> > >
> > > PastActivityAdapter (RecyclerAdapter)<br>
> > > * Coupled to UpcomingActFragment
> > >
> > > UpcomingActivityAdapter (PagedListAdapter)<br>
> > > * Coupled to UpcomingActFragment + MainFragment
> > >
> > > YourActivityAdapter (RecyclerAdapter)
> > > * Coupled to UpcomingActFragment
> >
> > > *newact*<br>
> > > NewActFragment
> >
> > > *upcomingact*<br>
> > > UpcomingActFragment
> >
> > > *viewmodel*<br>
> > > BookingActViewModel<br>
> > > * push to realtime db
> > >
> > > NewActViewModel<br>
> > > * room method: insert, deleteById, deleteByChecked (check if registered, delete from NewActivity db), deleteByClock (delete old data), 
> > > getAllNewActivitiesByClock, getAllNewActivitiesBySport, getAllNewActivitiesByClock2 (filter)
> > > * firebase realtime db + firestore read & write data: pull, push
> > >
> > > PastActViewModel<br>
> > > * room method: insert, getPastActivities
> > > * firebase realtime db: pullFrom("upcoming_activity", "your_activity")
> > >
> > > UpcomingActViewModel<br>
> > > * room method: insert, deleteByClock, deleteById, getAllUpcomingActivitiesByClock, getAllUpcomingActivitiesByClock2 (filter)
> > > * firebase realtime db + firestore read & write data: deleteFromDb, pull
> > > 
> > > YourActViewModel<br>
> > > * room method: insert, deleteById, getAllYourActivitiesByClockLimit10, getAllYourActivitiesByClock
> > > * firebase realtime db + firestore read & write data: deleteFromDb, pull
> > > 
> > MainFragment
> >
> > *user*
> > > *editprofile*<br>
> > > EditProfileFragment
> >
> > UserFragment<br>
> >
> > BaseInterface<br>
> > * Custom interfaces for listeners
> >     * BookingActListener
> >     * UpcomingActDeleteListener
> >     * RefreshListener
> 
> > MainActivity
> >
> > BaseViewModel
> > * Retrieve instances of CompositeDisposable, DataManager, SchedulerProvider

### util

> CustomSnapHelper
> * For recyclerview snaps
> 
> DateConverter
> * For date & time conversion
> 
> StringChecker
> * Checks strings & returns images / adapts strings
> 
> TextInputEditTextNoAutofill
> * Override autofill

### viewmodels

> ViewModelProviderFactory
> * Refer to Design Patterns (Miro), required for di


### Package
> BaseActivity
>
> BaseApplication
> * inject application component into BaseApplication
>
> BaseFragment
> * retrieves navController, disableAutoFill, setDialog, enqueueing task (for refresh)
> 
> SessionManager
> * used to keep track of auth status of user (register, login, logout, user instance)


### androidTest
> Instrumented testing cases using JUnit4Runner + Espresso

### test
> Unit testing cases using JUnit4 + Regex
