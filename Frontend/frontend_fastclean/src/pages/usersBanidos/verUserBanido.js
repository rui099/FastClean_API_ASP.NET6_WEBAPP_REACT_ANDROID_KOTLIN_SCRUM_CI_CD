import React, {Component} from 'react';
import './usersBanidos.css';
import {NavIcon } from './usersBanidosElements';
import axios from "axios";
import { getUser } from "../../services/auth";
import api from "../../services/api";
import {FaRedoAlt} from 'react-icons/fa';

var pathname = window.location.pathname;
var ID = pathname.split('/')[3];
var cargo = pathname.split('/')[2];

class verUsersBanidos extends Component {

  state = {
    infos: {},
    morada: {},
    error: "",
    sucess: ""
  };


  async componentDidMount() {
    
    //   if (user.cargo === "Administrador") {
      
    if ( cargo === "Cliente" ){
        const response1 = await axios.get(
            "https://localhost:7067/api/DetailsClientes/" + ID
        );
          this.setState({ infos: response1.data });
          this.setState({ morada: response1.data.morada });
    } else {
        const response2 = await axios.get(
            "https://localhost:7067/api/DetailsFuncionarios/" + ID
        );
          this.setState({ infos: response2.data });
          this.setState({ morada: response2.data.morada });
    }
     
    //}

  }

  desbanir = async e => {
  
    e.preventDefault();
  
    try{
      
      await api.put("desbanirUtilizador/" + ID, { ID });
      this.setState({ sucess: "O Utilizador foi Desbanido! " });
      document.getElementById("sucesso").style.color = "green";
      
      setTimeout(function(){
        document.getElementById("sucesso").innerHTML = '';
        window.location.href="/usersBanidos";
      }, 2000);
    } catch (err){
      this.setState({ error: "Erro ao desbanir o utilizador!" });
    }
  
  };

  render(){

    const { infos } = this.state;
    const { morada } = this.state;
    
  return (
      <div class="displayInfo">
        <br/>
       
        <h1>Utilizador Banido: {infos.nome} </h1>
        
        <div>
            <div class="divUB">
              <img
                src={`data:image/jpeg;base64,${infos.imagem}`}
                width="150"
                height="150" /><br /><br />
             
              <div>
                <label class="lblUB"><b> Nome: </b></label>
                <label> {infos.nome} </label><br /><br />
              </div>
              <div>
                <label class="lblUB"><b> Cargo: </b></label>
                <label> {infos.tipoUtilizador} </label><br /><br />
              </div>
              <div>
                <label class="lblUB"><b> Idade: </b></label>
                <label> {infos.idade} </label><br /><br />
              </div>
              <div>
                <label class="lblUB"><b> Morada: </b></label>
                <label> {morada.rua}, Nº {morada.numero} </label><br /><br />
              </div>
              <div>
                <label class="lblUB"><b> Zona Habitacional: </b></label>
                <label> {morada.distrito}, {morada.concelho}, {morada.freguesia} </label><br /><br />
              </div>
              <div>
                <label class="lblUB"><b> Código Postal: </b></label>
                <label> {morada.codigoPostal} </label><br /><br />
              </div>
              <div>
                <label class="lblUB"><b> Email: </b></label>
                <label> {infos.email}  </label><br /><br />
              </div>
              <div>
                <label class="lblUB"><b> Telemóvel: </b></label>
                <label> {infos.contacto} </label><br /><br />
              </div>
              <br />
            </div>
          </div>
          <div>
              {this.state.sucess && <p id="sucesso"><b>{this.state.sucess}</b></p>}
              {this.state.error && <p id="err"><b>{this.state.error}</b></p>} 
          </div>
          <div>
              <button class="btnUB" style={{backgroundColor: "darkgreen"}} onClick={this.desbanir}> <FaRedoAlt /> <b> Desbanir </b> </button>
          </div> <br/>
         <br/>
      </div>
  );
}
}

export default verUsersBanidos;