import React, { Component } from "react";
import Bingo from "../Bingo/Bingo";
import BingoList from "../BingoList/BingoList";

import "./editpage.less";

class EditPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            bingo: null,
            bingoList: []
        };
    }

    render() {
        return (
            
            <div className="main__content">  
                <div className="naim__auth-button" onClick={() => onLoginClick}>
                    Log In
                </div>
                <div className="main__bingo">
                    {(this.state.bingo != null) &&
                    <Bingo editable={false} base={this.state.bingo} />}                    
                </div>  
                <div className="main__bingo-list">
                    <BingoList onItemClick={bingo => this.onBingoListClick(bingo)}/>
                </div>               
            </div>
        );
    }

    componentDidMount() {
        this.updateBingoList();
    }

    onLoginClick() {
        let xhr = new XMLHttpRequest();
        xhr.open('GET', 'http://localhost:8089/login');
        xhr.onload = () => {
            let response = JSON.parse(xhr.response);
            this.setState({
                bingoList: (response == null) ? [] : response
            });
        };
        xhr.send();
    }

    onBingoListClick(bingo) {
        let xhr = new XMLHttpRequest();
        xhr.open('GET', 'http://localhost:8089/bingo/'+bingo.id);
        xhr.onload = () => {
            let response = JSON.parse(xhr.response);
            this.setState({
                bingo: response
            });
        };
        xhr.send();
    }
}

export default EditPage;