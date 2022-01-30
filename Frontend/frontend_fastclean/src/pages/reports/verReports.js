import React, {Component} from 'react';
import './reports.css';
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import { maxWidth } from "@material-ui/system";
import {FaExclamationTriangle, FaArchive ,FaArrowLeft} from "react-icons/fa";
import axios from "axios";
import { getCargo } from "../../services/auth";
import api from "../../services/api";

var pathname = window.location.pathname;
var ID = pathname.split('/')[3];
var cargoReporter = pathname.split('/')[2];
var reportedID = pathname.split('/')[4];

class verReports extends Component {
  
  state = {
    reportObj: {},
    id: "",
    error: "",
    sucess: ""
  };

  async componentDidMount() {
    
   if (getCargo() === "Administrador") {
      
    if (cargoReporter === "Funcionario") {
    const response = await axios.get(
      "https://localhost:7067/api/Reports/ReportClienteDetails/" + ID
    );
    this.setState({ reportObj: response.data });
    } else {
    const response = await axios.get(
      "https://localhost:7067/api/Reports/ReportFuncionarioDetails/" + ID
    );
    this.setState({ reportObj: response.data });
  }
}

}

handleArquivar = async e =>{
  
  e.preventDefault();

  try{
    
    await api.put("/Reports/Arquivar/" + ID, { ID });
    this.setState({ sucess: "Report arquivado com Sucesso!" });
    document.getElementById("sucesso").style.color = "green";
    setTimeout(function(){
      document.getElementById("sucesso").innerHTML = '';
      window.location.href = '/reports';
    }, 2000);

  } catch(err) {
      this.setState({ error: "Erro ao arquivar report!" });
  }

};


handleBanir = async e => {
  
  e.preventDefault();

  try{
    
    await api.put("banirUtilizador/" + reportedID, { reportedID });
    this.setState({ sucess: "Utilizador banido com sucesso!" });
    document.getElementById("sucesso").style.color = "green";
    setTimeout(function(){
      document.getElementById("sucesso").innerHTML = '';
      window.location.href = '/reports';
    }, 2000);
  } catch (err){
    this.setState({ error: "Erro ao banir utilizador!" });
  }

};


render(){

  const { reportObj } = this.state;

  return (
      <div class="displayInfo">
        <h1>Report nº {ID} - {cargoReporter}s</h1>
        <div>
          <h2>{reportObj.titulo}</h2>
          <h3>{reportObj.tipo}</h3>
          <div class="divPrim">
            <div class="divSeg"><h3 style={{color: "blue"}} >Reporter</h3><h4> {cargoReporter==="Funcionario" ? reportObj.funcionario : reportObj.cliente } ({cargoReporter})</h4></div>
            <div class="divTer"><h3 style={{color: "red"}} >Reported </h3><h4> {cargoReporter==="Funcionario" ? reportObj.cliente : reportObj.funcionario } ({cargoReporter==="Funcionario" ? "Cliente" : "Funcionário"})</h4></div>
          </div>
        <div> <br/>
          <hr/>
          <div><h3>Descrição</h3></div>
          <div style={{marginTop: 20, marginBottom: 20}}>
              
              {reportObj.descricao} 
          
          </div>
          <div >
          <div>
              {this.state.sucess && <p id="sucesso"><b>{this.state.sucess}</b></p>}
              {this.state.error && <p id="err"><b>{this.state.error}</b></p>} 
          </div>
            <a href='/reports' class="btnvoltar"> <FaArrowLeft/> Voltar </a>
            <button class="btnbanir" onClick={this.handleBanir} > <FaExclamationTriangle/> Banir </button>
            <button class="btnarquivo" onClick={this.handleArquivar} > <FaArchive/> Arquivar </button><br/><br/>
          </div>
        </div>
      </div>
      </div>
  );
}
}

export default verReports;