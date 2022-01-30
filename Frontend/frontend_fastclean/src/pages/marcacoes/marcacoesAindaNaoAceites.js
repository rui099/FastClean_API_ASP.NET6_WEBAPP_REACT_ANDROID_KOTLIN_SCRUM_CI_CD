import React, {Component} from 'react';
import Table from '@mui/material/Table';
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import {NavIcon} from './marcacoesElements';
import axios from "axios";
import { getUser} from "../../services/auth";


function refresh(){
  window.setTimeout(function(){
    window.location.reload();
  },5);
}


class marcacoesAindaNaoAceites extends Component {


  state = {
    marcacoes : []
  };

  async componentDidMount() {

    const user = JSON.parse(getUser());
    const Id = user.id;
    
    //   if (user.cargo === "Administrador") {
      
    const response = await axios.get(
        "https://localhost:7067/api/ListMarcacoesAindaNaoAceites/"+ Id
    );
    
    this.setState({ marcacoes: response.data });
  
    //}
  
    
  }
  
  render(){

    const { marcacoes } = this.state;

    return (
      <div class="displayInfo" >
        <h1> Lista de Marcações Ainda Não Aceites </h1>

        <TableContainer component={Paper} style={{borderRadius: 50}}>
          <Table
            sx={{ minWidth: 650, maxWidth: 2000 }}
            size="small"
            aria-label="a dense table">

            <TableHead> 
              <TableRow>
              <TableCell style={{textAlign:'center'}}> <h3> ID Marcação </h3></TableCell>
                <TableCell style={{textAlign:'center'}}> <h3> Funcionário </h3></TableCell>
                <TableCell style={{textAlign:'center'}}> <h3> Cliente </h3></TableCell>
                <TableCell style={{textAlign:'center'}}> <h3> Tipo de Marcação </h3></TableCell>
                <TableCell style={{textAlign:'center'}}> <h3> Data Inicio </h3></TableCell>
                <TableCell style={{textAlign:'center'}}> <h3> Ação </h3></TableCell>
              </TableRow>
            </TableHead>

            <TableBody>
              {marcacoes.map((marcacao) => ( 
                <TableRow
                  key={marcacao.nome}
                  sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                >
                  <TableCell style={{textAlign:'center', fontSize:18}}> {marcacao.marcacaoId} </TableCell>
                  <TableCell style={{textAlign:'center', fontSize:18}}> {marcacao.funcionario} </TableCell>
                  <TableCell style={{textAlign:'center', fontSize:18}}> {marcacao.cliente}</TableCell>
                  <TableCell style={{textAlign:'center', fontSize:18}}> {marcacao.tipoLimpeza} </TableCell>
                  <TableCell style={{textAlign:'center', fontSize:18}}> {marcacao.diaHora} </TableCell>
                  
                  <TableCell> 
                 
                    <NavIcon to={"/marcacaoDetails/" + marcacao.tipoLimpeza + "/" + marcacao.marcacaoId } onClick={refresh} >
                        
                        <img  
                        src={require("../../images/eye.png").default}
                        width="60"
                        height="40"/>

                    </NavIcon>
                        
                </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </div>
    );

  }
}

export default marcacoesAindaNaoAceites;

  

