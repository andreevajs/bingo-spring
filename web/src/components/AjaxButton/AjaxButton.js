import React, { Component } from "react";

class AjaxButton extends Component {
    constructor(props) {
        super(props);
        this.bingoList = [];
    }

    render() {
        return (            
            <button onClick={() => this.onClick()}>
                {this.props.content}
            </button>
        );
    }

    onClick() {
        let xhr = new XMLHttpRequest();
        xhr.open(this.props.method, 'http://localhost:8089' + this.props.url);
        xhr.onload = () => {
            this.props.responseHandler(hr.response);
        };
        xhr.send(this.props.data);
    }
}

export default AjaxButton;