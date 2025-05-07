# SecureVault - Password Manager App

SecureVault is a secure, user-friendly mobile application that allows users to store and manage their passwords in an organized manner. The app uses strong encryption algorithms to ensure the security of sensitive data while providing an intuitive interface for easy password management.

![SecureVault App Banner](https://example.com/securevault-banner.png)

## Features

### Core Functionality
- **Password Storage**: Securely add and store passwords with details like account type, username/email, and password
- **Password Management**: View, edit, and delete saved passwords
- **Organized Dashboard**: Clear list view of all saved passwords on the home screen
- **Data Security**: AES-256 encryption for all sensitive data
- **Local Storage**: All data is stored locally on the device using a secure database

### Technical Implementation
- **Encryption**: Strong AES encryption for password data
- **Secure Database**: Local database implementation with encrypted storage
- **Intuitive UI**: Clean, modern interface designed for ease of use
- **Input Validation**: Ensures all required fields are properly filled
- **Error Handling**: Graceful handling of edge cases for a smooth user experience

## App Screenshots
<div style="display: flex; justify-content: center; gap: 10px;">
  <img src="https://github.com/user-attachments/assets/569d1b53-2e84-4a03-9fa4-5207c421ba0a" alt="Screenshot 1" width="200"/>
  <img src="https://github.com/user-attachments/assets/68c8d24b-03ab-41e8-9936-e64644a781c7" alt="Screenshot 2" width="200"/>
  <img src="https://github.com/user-attachments/assets/830fd380-b885-4b7b-895d-73171c0a8d03" alt="Screenshot 3" width="200"/>
</div>

## Technology Stack
- **Programming Language**: Kotlin (Android)
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room Database (Android) 
- **Encryption**: AES-256 encryption
- **UI Components**: Jetpack Compose / Material Design (Android)

## Getting Started

### Prerequisites
- Android Studio 
- Android SDK 23+ (Android 6.0 Marshmallow or newer) 
- Git

### Installation

1. Clone the repository
```bash
[git clone https://github.com/yourusername/securevault.git](https://github.com/oyehanif/PasswordManager.git)
```

2. Open the project in Android Studio

3. Build and run the application on an emulator or physical device

## How to Use

### Adding a New Password
1. Launch the SecureVault app
2. Tap the "+" button in the bottom right corner of the home screen
3. Fill in the required fields:
   - Account Type (e.g., Gmail, Facebook)
   - Username/Email
   - Password
4. Tap "Save" to securely store the password

### Viewing Saved Passwords
1. All saved passwords are displayed on the home screen
2. Tap on any password card to view its details

### Editing a Password
1. Navigate to the password details screen by tapping on a password card
2. Tap the "Edit" button
3. Modify the necessary fields
4. Tap "Save" to update the password information

### Deleting a Password
1. Navigate to the password details screen
2. Tap the "Delete" button
3. Confirm the deletion when prompted

## Security Considerations

- All passwords are encrypted using AES-256 encryption before being stored in the database
- The app does not transmit any data over the network
- All data is stored locally on the device
- Input validation ensures data integrity

## Future Enhancements

While not implemented in the current version due to time constraints, the following features could be added in future updates:

- Biometric authentication (fingerprint/face recognition)
- Password strength meter
- Random password generator
- Auto-fill service integration
- Cloud backup options (with end-to-end encryption)

## Development Approach

1. **Planning**: Analyzed requirements and designed the app architecture
2. **UI Implementation**: Created the user interface based on the Figma design
3. **Database Setup**: Implemented the local database with encryption
4. **Core Functionality**: Developed password management features
5. **Testing**: Performed unit and integration testing
6. **Documentation**: Created comprehensive README and code documentation

## Limitations

- The app currently does not include the bonus features (biometric authentication, password strength meter, password generation)
- The UI follows the Figma design but may have slight variations due to platform constraints

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contact

Email- hanifshaikh8153@gmail.com
