import React, {Component} from 'react';
import './candidatosClientes.css';
import {NavIcon } from './candidatosClientesElements';
import axios from "axios";
import { getCargo } from "../../services/auth";
import api from "../../services/api";

var pathname = window.location.pathname;
var ID = pathname.split('/')[2];
var ccFile = pathname.split('/')[3];
var cadastro = pathname.split('/')[4];

class verCandidatosClientes extends Component {

  state = {
    users: {},
    morada: {},
    error: "",
    sucess: ""
  };


  async componentDidMount() {
    
    if (getCargo() === "Administrador") {
      
      const response = await axios.get(
        "https://localhost:7067/api/DetailsClientes/" + ID
      );
      this.setState({ users: response.data });
      this.setState({ morada: response.data.morada });
    
   }

  };

 ccFileFunction = async e => {
  e.preventDefault();

   const response2 = await axios.get(
    "https://localhost:7067/api/" + ID + "/" + ccFile, {responseType:"blob"}
    ).then((response2) => {
      const url = window.URL.createObjectURL(new Blob([response2.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', ccFile); 
      document.body.appendChild(link);
      link.click();
  });;
  
 }

 cadastroFunction = async e => {
  e.preventDefault();

   const response5 = await axios.get(
    "https://localhost:7067/api/" + ID + "/" + cadastro, {responseType:"blob"}
    ).then((response5) => {
      const url = window.URL.createObjectURL(new Blob([response5.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', cadastro); 
      document.body.appendChild(link);
      link.click();
  });;
  
 }

  aceitarUser = async e => {
  
    e.preventDefault();
  
    try{
      
      await api.put("aceitarUtilizador/" + ID, { ID });
      this.setState({ sucess: "O Candidato foi Aceite! " });
      document.getElementById("sucesso").style.color = "green";
      
      setTimeout(function(){
        document.getElementById("sucesso").innerHTML = '';
        window.location.href = '/usersAceitesClientes';
      }, 2000);
    } catch (err){
      this.setState({ error: "Erro ao aceitar o candidato!" });
    }
  
  };


  recusarUser = async e => {
  
    e.preventDefault();
  
    try{
      window.location.href = '/candidatosClientes';
    } catch (err){
      this.setState({ error: "Erro ao recusar o candidato!" });
    }

  }; 

  render(){
    
    const { users } = this.state;
    const { morada } = this.state;

  return (
      <div class="displayInfo">
        <br/>
       
        <h1>Informação Pessoal de {users.nome} </h1>
        
        <div>
            <div class="divLCandCli">
              <img
                src={`data:image/jpeg;base64,${users.imagem}`}
                width="150"
                height="150" /><br /><br />
             
              <div>
                <label class="lblCandCli"><b> Nome: </b></label>
                <label> {users.nome} </label><br /><br />
              </div>
              <div>
                <label class="lblCandCli"><b> Idade: </b></label>
                <label> {users.idade} </label><br /><br />
              </div>
              <div>
                <label class="lblCandCli"><b> Morada: </b></label>
                <label> {morada.rua}, Nº {morada.numero} </label><br /><br />
              </div>
              <div>
                <label class="lblCandCli"><b> Zona Habitacional: </b></label>
                <label> {morada.distrito}, {morada.concelho}, {morada.freguesia} </label><br /><br />
              </div>
              <div>
                <label class="lblCandCli"><b> Código Postal: </b></label>
                <label> {morada.codigoPostal} </label><br /><br />
              </div>
              <div>
                <label class="lblCandCli"><b> Email: </b></label>
                <label> {users.email}  </label><br /><br />
              </div>
              <div>
                <label class="lblCandCli"><b> Telemóvel: </b></label>
                <label> {users.contacto} </label><br /><br />
              </div>

              <br />
           
            </div>
            <div>
              <hr /><br />
              <h1> Anexos </h1>
              <div class="divLCandCli">
                <fieldset style={{border: 2, color: "blue", borderStyle:"dotted"}}>
                <div>
                <button style= {{backgroundColor: 'white', marginLeft: 0, width: "auto", height: "auto"}} onClick={this.ccFileFunction} > <img class="anexosCandCli"
                    src={require("../../images/doc.png").default}
                    width="50"
                    height="50" /> </button><figcaption><b>Cartão de Cidadão</b></figcaption><hr/>
                </div> 
                <div>
                <button style= {{backgroundColor: 'white', marginLeft: 0, width: "auto", height: "auto"}} onClick={this.cadastroFunction} > <img class="anexosCandCli"
                    src={require("../../images/doc.png").default}
                    width="50"
                    height="50" /> </button><figcaption><b>Cadastro Pessoal</b></figcaption>
                </div> 
                </fieldset>
              </div>  
            </div>
          </div>
          <div>
              {this.state.sucess && <p id="sucesso"><b>{this.state.sucess}</b></p>}
              {this.state.error && <p id="err"><b>{this.state.error}</b></p>} 
          </div>
          <div>
              <button class="btnCandCli" onClick={this.aceitarUser}> Aceitar </button>
              <button class="btnCandCli" onClick={this.recusarUser}> Rejeitar </button>
          </div> <br/>
      </div>
  );
}
}

export default verCandidatosClientes;