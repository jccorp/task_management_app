# Getting Started with Task Management Front End

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

## Prerequisites
* Have Nodejs installed.
* Have Docker installed (For running the app in a container)

This app was developed with the Reactjs. [more info](https://reactjs.org/)

After cloned this repo you must execute the following command to install the dependencies:
### `npm install`

### .env
In the file .env is where you configure the url of the backend application.
So you must set REACT_APP_API_ENDPOINT variable in order to make the app work correctly.
**DONT COMMIT THE CHANGES OF THIS FILE**

After that you are ready to start the app en dev mode:
### `npm start`
Open [http://localhost:3000](http://localhost:3000) to view it in your browser.

### Reports
Unit test reports are under the coverage folder of this repo:
* lcov-report/index.html

## Docker

The application includes a Dockerfile to execute it in a
container.
To generate the image you should execute this command:

### `docker build --tag=task-management-fe:latest .`

To execute the container type the following command:
### `docker run -d -p3000:3000 task-management-fe:latest`


## Available Scripts

In the project directory, you can run:

### `npm start`

Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in your browser.

The page will reload when you make changes.\
You may also see any lint errors in the console.

### `npm test`

Launches the test runner in the interactive watch mode.\
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `npm run build`

Builds the app for production to the `build` folder.\
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.\
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.
