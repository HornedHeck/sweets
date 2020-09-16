## 'Nobody runs in your family...'
A 'Tabata timer' application. ([Reference](https://play.google.com/store/apps/details?id=com.evgeniysharafan.tabatatimer&hl=ru))

1. A user can create different timer sequences and switch between them (use GSON or SQLite for data storage).
2. Home page - a list of sequences
   - CRUD for them
3. Sequence properties:
   - title
   - colour
4. Timer page:
   - remaining time of the current phase
   - list of upcoming phases
   - controls to pause, go back and forth through the sequence or to leave this page and cancel the timer
5. Edit page:
   - tweak duration of each phase (workout, rest, warm-up, cooldown)
   - change number of phase repetitions and rest periods between them
   - define sequence properties
6. While running, each phase switch should trigger a sound to warn user.
7. The timer should not stop when user leaves the app (see Services).
8. Settings page (implemented with AndroidX Preference library):
   - day / night theme
   - change app font size (without app reload)
   - a button to clear all stored data
   - switch app locale (a choice between two languages at least).
9. Application should have a splash screen with app name or artwork to entertain the user while the app is being loaded.

**Topics to cover:** data storage, app architecture, styling, services, preferences.

### Further reading:
- [Permissions](https://developer.android.com/guide/topics/permissions/overview)
- [Room Database](https://developer.android.com/training/data-storage/room) / [Data storage](https://developer.android.com/training/data-storage/app-specific)
- [Services](https://developer.android.com/guide/components/services)
- [Preferences](https://developer.android.com/guide/topics/ui/settings)
- [Style and theme](https://developer.android.com/guide/topics/ui/look-and-feel/themes)

