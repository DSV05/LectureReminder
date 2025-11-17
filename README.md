# LectureReminderApp

Welcome to **LectureReminderApp** — a lightweight LectureReminderApp is a mobile application designed to help students effectively manage their academic schedules by providing timely reminders for lectures. The app allows users to add, modify, and delete lecture schedules, and notifies them before the lecture begins. With a simple and intuitive interface, the application focuses on improving time management and reducing missed lectures.

📌 Features  
- Add new lectures: specify title, date, time.
  
- Edit or delete existing lecture entries.
  
- Set up notifications ahead of lecture time so you don’t miss class.
  
- Simple and clean UI built with modern Kotlin and Android architecture.
  
- Local storage via Room/SQLite (or equivalent) ensuring your data persists on-device.

 🧱 Project Structure  

LectureReminderApp/
│

├── app/ # Android app module

│ ├── src/main/java/... # Kotlin source files

│ ├── src/main/res/... # Resources (layouts, drawables, strings)

│ └── AndroidManifest.xml

├── .idea/ # IntelliJ/Android Studio settings

├── build.gradle.kts # Root Gradle build script

├── gradle.properties

├── gradlew / gradlew.bat

└── settings.gradle.kts

🎯 How To Use The App

-Launch the app on your Android device or emulator.

-Tap “Add Lecture” and fill in the details: title, date, time.

-Optionally select “Repeat Weekly” if this lecture recurs.

-Save the lecture and you’ll receive a notification prior to the scheduled time.

-You may edit or delete lectures from the main lecture list.

📌 Functionality and Concepts Used

- Room: App is completely offline, where user don't need to have an active internet connection to use the app.
  
- Architecture: Uses clean architecture to organize and maintain the code.
  
- User have complete flexibility on adding and removing the classes from schedule and editing it.
  
- Kotlin: Application is completely written (100%) in Kotlin.
  
- Material Components: Uses material ui components to make app look more sleek, interactive and easy to use.

🎯 Future Scope

This app can be integrated with more features to solve problems daily faced by students such as to find last year papers/notes during exam time,
\a place where community events are shared and a place to share more of job opportunities and combinedly create a complete college app for students.

*Add Lecture*:

<img width="371" height="744" alt="Screenshot 2025-11-17 204245" src="https://github.com/user-attachments/assets/57a38d68-0479-4820-8b9d-07f9fd4b81e2" />

*Add Subject*:

<img width="366" height="829" alt="Screenshot 2025-11-17 204332" src="https://github.com/user-attachments/assets/39c2ae24-2a25-4edf-988b-a6342ff1a8c2" />

*Select Date*:

<img width="369" height="825" alt="Screenshot 2025-11-17 204421" src="https://github.com/user-attachments/assets/98ba94e8-1dfd-4216-8195-f5156cd355a8" />

*Select Time*:

<img width="365" height="831" alt="Screenshot 2025-11-17 204439" src="https://github.com/user-attachments/assets/1be244c4-94ac-461d-b261-716296343641" />

