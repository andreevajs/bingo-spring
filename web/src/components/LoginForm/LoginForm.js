import React, { Component } from "react";

import "./loginform.less";

class LoginForm extends Component {
    constructor(props) {
        super(props);
        this.bingoList = [];
    }

    render() {
        return (            
            <div className="login-form__wrap">
                <div className="login-form__title">LOGIN</div>
                <form action="http://localhost:8089/login" method="POST">
                    <input className="login-form__input" type="text" id="username" name="username" placeholder="username"/>
                    <input className="login-form__input" type="password" id="password" name="password" placeholder="password"/>
                    <input className="login-form__submit" type="submit" value="Ok"/>
                </form>
            </div>
        );
    }

    onSubmit(e) {
        e.preventDefault();
        let xhr = new XMLHttpRequest();
        xhr.open('post', 'http://localhost:8089/login');
        xhr.onload = () => {
            alert(xhr.response);
        };
        xhr.send(new FormData(e.target));
    }
}

export default LoginForm;