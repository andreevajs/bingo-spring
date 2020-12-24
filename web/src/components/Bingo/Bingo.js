import React, { Component, createRef } from "react";

import "./bingo.less";

class Bingo extends Component {
    constructor(props) {
        super(props);

        props.createRef(this);

        this.rows = 3;
        this.columns = 3;
        this.cells = [];
        
        this.state = {
            loading: true,
            base: { 
                id: null,
                name: "",
                canEdit: true,
                cells: []
            }
        }

        this.reloadCells(this.state.base);
    }


    render() {
        if (this.state.loading)
            return "";
        else
            return (
                <div>
                    <input className="bingo__name" type="text" 
                        {...(this.state.base.canEdit) ? {disabled: false} : {disabled: true}} 
                        defaultValue={this.state.base.name}
                        onChange={(e) => this.onNameChange(e)}
                        onBlur={(e) => this.saveBingoSettings(e)}/>
                    <div className="bingo__table-wrap">
                        <table className="bingo__table">
                            {this.cells.map(row =>
                            <tr>{ row.map(cell =>
                                <td className="bingo__table-td">
                                    <textarea className="bingo__cell" 
                                        {...(this.state.base.canEdit) ? {disabled: false} : {disabled: true}} 
                                        defaultValue={cell.content}
                                        onChange={(e) => this.onCellTextChange(cell, e)}
                                        onBlur={(e) => this.saveCell(cell)}/>                                
                                </td>)}
                            </tr>)}
                        </table>
                    </div>
                    <button className="bingo__submit" onClick={() => this.resetBingo()}>
                        new
                    </button>
                </div>
            );
    }

    onNameChange(e) {
        this.state.base.name = e.target.value;
    }

    onCellTextChange(cell, e) {
        cell.content = e.target.value;
    }

    saveCell(cell, e) {
        if (!this.state.base.canEdit || this.state.base.id == null)
            return;
        let xhr = new XMLHttpRequest();
        xhr.open('POST', 'http://localhost:8089/bingocell?bingo='+this.state.base.id);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onload = () => {
            if (xhr.status == 200 || xhr.status == 201)
                console.log("saved");
        };
        xhr.send(JSON.stringify(cell));
    }

    saveBingoSettings(e) {
        if (!this.state.base.canEdit)
            return;
        let xhr = new XMLHttpRequest();
        xhr.open('POST', 'http://localhost:8089/bingo');
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onload = () => {
            if (xhr.status == 200 || xhr.status == 201)
                console.log("saved");
        };
        xhr.send(JSON.stringify(this.state.base));
    }

    load(id) {
        this.setState({
            loading: true
        })
        let xhr = new XMLHttpRequest();
        xhr.open('GET', 'http://localhost:8089/bingo/'+id);
        xhr.onload = () => {
            let response = JSON.parse(xhr.response);
            this.reloadCells(response);
        };
        xhr.send();
    }    

    submit() {
        
    }

    reloadCells(base) {
        this.rows = 3;
        this.columns = 3;
        
        base.cells.forEach(cell => {
            this.rows = Math.max(this.rows, cell.row);
            this.columns = Math.max(this.columns, cell.column);
        });

        this.cells = [];
        for(let r = 0; r < this.rows; r++){
            let row = [];
            for(let c = 0; c < this.columns; c++){
                row.push({id: null, content: null, row: r + 1, column: c + 1});
            }
            this.cells.push(row);
        }        
        
        base.cells.forEach(cell => {
            this.cells[cell.row - 1][cell.column - 1] = {
                id: cell.id,
                content: cell.content,
                row: cell.row,
                column: cell.column
            };
        });

        this.setState({
            loading: false,
            base: base
        });
    }

    resetBingo() {
        this.setState({
            loading: true
        }, () => this.setState({
            loading: false,
            base: {
                id: null,
                name: "new bingo",
                canEdit: true,
                cells: [],
                backgroundColor: null
            }
        }));

    }
}

export default Bingo;