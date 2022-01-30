import React, {Component} from 'react';
import Table from '@mui/material/Table';
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import { maxWidth } from "@material-ui/system";
import {NavIcon } from './usersBanidosElements';
import axios from "axios";
import { getUser } from "../../services/auth";
import api from "../../services/api";


function refresh() {
    window.setTimeout(()=>{
        window.location.reload();
    }, 5);
  }


  class usersBanidos extends Component {
    
    state = {
        banidos: []  
    };

    async componentDidMount() {
     
        //   if (user.cargo === "Administrador") {
      
        const response = await axios.get(
            "https://localhost:7067/api/TodosUtilizadoresBanidos"
        );
        
        this.setState({ banidos: response.data });
    
        //}

    }

    render(){

      const { banidos } = this.state;
        
      return (
    
         <div> 
            <TableContainer component={Paper} style={{borderRadius: 30, width: 1000}}>
            <h1 style={{textAlign: 'center'}}> Lista de Utilizadores Banidos </h1>
              <Table
                sx={{ minWidth: 650, maxWidth: 2000 }}
                size="small"
                aria-label="a dense table"
              >
    
                <TableHead> 
                  <TableRow>
                    <TableCell style={{textAlign:'center'}}> <h3> Imagem </h3></TableCell>
                    <TableCell style={{textAlign:'center'}}> <h3> Nome </h3></TableCell>
                    <TableCell style={{textAlign:'center'}}> <h3> Cargo </h3></TableCell>
                    <TableCell style={{textAlign:'center'}}> <h3> Idade </h3></TableCell>
                    <TableCell style={{textAlign:'center'}}> <h3> Email </h3></TableCell>
                    <TableCell style={{textAlign:'center'}}> <h3 style={{wordWrap: 'break-word'}}> Zona Residencial <br/> (Freguesia, Concelho, Distrito) </h3></TableCell>
                    <TableCell style={{textAlign:'center'}}> <h3> Ação </h3></TableCell>
                  </TableRow>
                </TableHead>
    
                <TableBody>
                  {banidos.map((banido) => ( 
                    
                    <TableRow
                      key={banidos.nome}
                      sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                    >
                      <TableCell component="th" scope="row">
                       
                      <img 
                        src={`data:image/jpeg;base64,${banido.imagem}`} 
                        width="120"
                        height="120"
                      />
    
                      </TableCell>
    
                      <TableCell style={{textAlign:'center', fontSize:18}}> {banido.nome} </TableCell>
                      <TableCell style={{textAlign:'center', fontSize:18}}> {banido.tipoUtilizador} </TableCell>
                      <TableCell style={{textAlign:'center', fontSize:18}}> {banido.idade} anos </TableCell>
                      <TableCell style={{textAlign:'center', fontSize:18}}> {banido.email} </TableCell>
                      <TableCell style={{textAlign:'center', fontSize:18}}> {banido.morada.freguesia}, {banido.morada.concelho}, {banido.morada.distrito} </TableCell>
                      <TableCell style={{textAlign:'center', fontSize:16}}> 
                      <NavIcon to= {"/verUserBanido/" + banido.tipoUtilizador + "/" + banido.id } onClick={refresh} >
                        
                      <img  
                          src={require("../../images/eye.png").default}
                          width="60"
                          height="40" 
                      />
                      
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
    
export default usersBanidos;