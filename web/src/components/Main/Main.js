import React, { Component } from "react";
import Bingo from "../Bingo/Bingo";
import BingoList from "../BingoList/BingoList";

import "./main.less";

class Main extends Component {
    constructor(props) {
        super(props);

        this.bingo = null;
        this.state = {
            bingo: null,
            bingoList: []
        };
    }

    render() {
        return (
            
            <div className="main__content">  
                <a href='http://localhost:8089/login' className="main__auth-button">
                    Log In
                </a>
                <div className="main__bingo">
                    <Bingo createRef={ref => this.bingo = ref} />                   
                </div>  
                <div className="main__bingo-list">
                    <BingoList onItemClick={bingo => this.onBingoListClick(bingo)}/>
                </div>               
            </div>
        );
    }


    onBingoListClick(bingo) {
        this.bingo.load(bingo.id);
    }
}

export default Main;