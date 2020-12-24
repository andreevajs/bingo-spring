import React from "react";
import ReactDOM from "react-dom";
import Main from "./components/Main/Main";
import { HashRouter as Router, Route } from "react-router-dom";

import "./reset.css";
import "./res/fonts/fonts.css";
import "./styles.less";
import EditPage from "./components/EditPage/EditPage";


ReactDOM.render(
    <Main />,
    document.getElementById("root")
);