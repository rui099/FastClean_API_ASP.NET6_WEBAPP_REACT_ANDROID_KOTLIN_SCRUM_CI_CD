import React, {Component} from 'react';
import axios from "axios";
import '../marcacoes/marcacoesDetails.css';



var pathname = window.location.pathname;
var ID = pathname.split('/')[3];


class marcacaoDetails extends Component {

  state = {
    marcacaoObj: {}
  };

  async componentDidMount() {
    
    const response = await axios.get(
        "https://localhost:7067/api/Marcacoes/"+ ID
    );
    this.setState({ marcacaoObj: response.data });
    
  }


  render(){
    const { marcacaoObj } = this.state;
    
    
  return (
      <div class="displayInfo">
        <br/>
       
        <h1>Informação Marcação: {marcacaoObj.marcacaoId} </h1>

        <hr/><br/>
        
        <div>
            <div class="divL">
              <div>
                <label class="lbl"><b> Tipo de Imovel: </b></label>
                <label> {marcacaoObj.tipoImovel} </label><br /><br />
              </div>
              <div>
                <label class="lbl"><b> Tipo de Limpeza: </b></label>
                <label> {marcacaoObj.tipoLimpeza} </label><br /><br />
              </div>
              <div>
                <label class="lbl"><b> Morada da Marcação: </b></label>
                <label> {marcacaoObj.moradaMarcacao} </label><br /><br />
              </div>
              <div>
                <label class="lbl"><b> Número de Quartos: </b></label>
                <label> {marcacaoObj.numQuartos} </label><br /><br />
              </div>
              <div>
                <label class="lbl"><b> Número de Casas de Banho: </b></label>
                <label> {marcacaoObj.numCasasDeBanho}  </label><br /><br />
              </div>
              <div>
                <label class="lbl"><b> Cozinha: </b></label>
                <label> {marcacaoObj.cozinha === "False" ? "Sim" : "Não"} </label><br /><br />
              </div>
              <div>
                <label class="lbl"><b> Sala: </b></label>
                <label> {marcacaoObj.sala === "False" ? "Sim" : "Não"} </label><br /><br />
              </div>
              <div>
                <label class="lbl"><b> Detalhes: </b></label>
                <label> {marcacaoObj.detalhes === null ? "Não foram introduzidos detalhes do Serviço!!!" : marcacaoObj.detalhes}</label><br /><br />
              </div>
              <div>
                <label class="lbl"><b> Horario da Marcação: </b></label>
                <label> {marcacaoObj.diaHora} </label><br /><br />
              </div>
              <div>
                <label class="lbl"><b> Hora Inicio: </b></label>
                <label> {marcacaoObj.horaInicio === null ? "Ainda não começou o Serviço!!!!" : marcacaoObj.horaInicio} </label><br /><br />
              </div>
              <div>
                <label class="lbl"><b> Hora Final: </b></label>
                <label> {marcacaoObj.horaFinal === null ? "Ainda não acabou o Serviço!!!!" : marcacaoObj.horaFinal} </label><br /><br />
              </div>
              <div>
                <label class="lbl"><b> Nome Cliente: </b></label>
                <label> {marcacaoObj.cliente} </label><br /><br />
              </div>
              <div>
                <label class="lbl"><b> Nome Funcionário: </b></label>
                <label> {marcacaoObj.funcionario} </label><br /><br />
              </div>
              <div>
                <label class="lbl"><b> Esta Terminada:  </b></label>
                <label> {marcacaoObj.terminada === "False" ? "Não esta terminada!!!" : "Esta terminada!!!"}</label><br /><br />
              </div>
              <div>
                <label class="lbl"><b> Número de Horas Previstas: </b></label>
                <label> {marcacaoObj.numHorasPrevistas} </label><br /><br />
              </div>
              <div>
                <label class="lbl"><b> Duração Do Serviço: </b></label>
                <label> {marcacaoObj.duracao === null ? "Serviço ainda não terminado!!!" : marcacaoObj.duracao} </label><br /><br />
              </div>
              <div>
                <label class="lbl"><b> Total a Pagar: </b></label>
                <label> {marcacaoObj.total === 0.0 ? "Ainda não acabou o serviço!!!" : marcacaoObj.total} </label><br /><br />
              </div>
              <br />
                
            </div>
            

          </div>
            
      </div>
  );
}
}

export default marcacaoDetails;