import React, { Component } from "react";

import "./bingolist.less";

class BingoList extends Component {
    constructor(props) {
        super(props);

        this.state ={
            items: []
        };
    }


    render() {
        return (
            <div className="bingo-list__container">
                <div className="bingo-list__update-button" onClick={()=>this.updateBingoList()}>
                    UPDATE
                </div>
                {this.state.items.map(bingo => 
                    <div className="bingo-list__item" onClick={()=>this.onItemClick(bingo)}>
                        {bingo.name}
                    </div>
                )}
            </div>
        );
    }

    componentDidMount() {
        this.updateBingoList();
    }

    onItemClick(bingo) {
        this.props.onItemClick(bingo);
    }

    updateBingoList() {
        let xhr = new XMLHttpRequest();
        xhr.open('GET', 'http://localhost:8089/bingo');
        xhr.onload = () => {
            let response = JSON.parse(xhr.response);
            this.setState({
                items: (response == null) ? [] : response
            });
        };
        xhr.send();
    }
}

export default BingoList;