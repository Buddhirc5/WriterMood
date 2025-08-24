# Contributing to WriterMood 📝✨

Thank you for your interest in contributing to WriterMood! This document provides guidelines and information for contributors.

## 🤝 How to Contribute

### Reporting Bugs
- Use the [bug report template](/.github/ISSUE_TEMPLATE/bug_report.md)
- Provide detailed steps to reproduce the issue
- Include device information and screenshots if applicable

### Suggesting Features
- Use the [feature request template](/.github/ISSUE_TEMPLATE/feature_request.md)
- Describe the problem and proposed solution
- Consider implementation complexity and user impact

### Code Contributions
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Make your changes
4. Test thoroughly
5. Commit your changes (`git commit -m 'Add amazing feature'`)
6. Push to the branch (`git push origin feature/amazing-feature`)
7. Open a Pull Request

## 🛠️ Development Setup

### Prerequisites
- Android Studio Arctic Fox or later
- Kotlin 1.8+
- Android SDK 33+
- Git

### Local Development
1. Clone your fork: `git clone https://github.com/YOUR_USERNAME/writermood.git`
2. Open in Android Studio
3. Sync Gradle files
4. Build and run the project

## 📋 Code Style Guidelines

### Kotlin
- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable and function names
- Add comments for complex logic
- Keep functions small and focused

### Compose
- Use descriptive composable function names
- Follow Material Design principles
- Optimize for performance with proper state management
- Use preview functions for UI components

### Architecture
- Follow MVVM pattern
- Use Repository pattern for data access
- Implement proper error handling
- Write unit tests for ViewModels and Repositories

## 🧪 Testing

### Unit Tests
- Write tests for all ViewModels
- Test Repository methods
- Mock dependencies appropriately
- Aim for >80% code coverage

### UI Tests
- Test critical user flows
- Use Compose testing utilities
- Test accessibility features
- Verify theme changes

## 📝 Commit Guidelines

Use conventional commit format:
```
type(scope): description

[optional body]

[optional footer]
```

Types:
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes
- `refactor`: Code refactoring
- `test`: Adding tests
- `chore`: Maintenance tasks

## 🔍 Pull Request Process

1. **Update Documentation**: Update README.md if needed
2. **Add Tests**: Include tests for new functionality
3. **Follow Templates**: Use the provided PR template
4. **Code Review**: Address reviewer feedback
5. **Merge**: Maintainer will merge after approval

## 🏷️ Issue Labels

- `bug`: Something isn't working
- `enhancement`: New feature or request
- `documentation`: Improvements to documentation
- `good first issue`: Good for newcomers
- `help wanted`: Extra attention is needed
- `priority: high`: High priority issues
- `priority: low`: Low priority issues

## 📞 Getting Help

- **Issues**: Use GitHub issues for bugs and feature requests
- **Discussions**: Use GitHub Discussions for questions and ideas
- **Code Review**: Ask questions in PR comments

## 🎯 Areas for Contribution

### High Priority
- Performance optimizations
- Bug fixes
- Accessibility improvements
- Test coverage

### Medium Priority
- UI/UX improvements
- New features
- Documentation updates
- Code refactoring

### Low Priority
- Minor UI tweaks
- Code style improvements
- Additional themes
- Localization

## 📄 License

By contributing to WriterMood, you agree that your contributions will be licensed under the MIT License.

## 🙏 Recognition

Contributors will be recognized in:
- README.md contributors section
- Release notes
- GitHub contributors page

Thank you for contributing to WriterMood! 🚀
