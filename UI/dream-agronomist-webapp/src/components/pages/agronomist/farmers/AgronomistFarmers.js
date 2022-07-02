import {
    Button,
    Container,
    Dropdown,
    DropdownMenu,
    DropdownToggle,
    Icon, Input, Modal, ModalBody, ModalFooter, ModalHeader, Nav, NavItem, NavLink,
    notify,
    Select,
    Table,
    UncontrolledTooltip
} from "design-react-kit";
import React, {useEffect, useState} from "react";
import ApiClient from "../../../../commons/apiClient";
import {IconButton, Menu, MenuItem, Tab, Tabs, Tooltip} from "@mui/material";

function TableRowFarmers(props){
    const client=new ApiClient()
    const [openModal1, setOpenModal1] = useState(false);
    const [openModal2, setOpenModal2] = useState(false);
    const [openModal3, setOpenModal3] = useState(false);
    const [products,setProducts] = useState(false)
    const [productsForSelect,setProductsForSelect] = useState(false)
    const [selectedOption,setSelectedOption] = useState(null)
    const [productions,setProductions] = useState(null)
    const [tabSelected,setTabSelected]=useState("production")
    const [anchorEl, setAnchorEl] = React.useState(null)
    const openMenu = Boolean(anchorEl);
    const [newDate, setNewDate]=useState("")
    const [newStartTime, setNewStartTime]=useState("")
    const [newEndTime, setNewEndTime]=useState("")


    const [newPh, setNewPh]=useState("")
    const [newNitrogen, setNewNitrogen]=useState("")
    const [newOrganicCarbon, setNewOrganicCarbon]=useState("")
    const [newPhosphorus, setNewPhosphorus]=useState("")
    const [newLimestone, setNewLimestone]=useState("")

    const toggleModal1=()=>{
        if(openModal1){
            setOpenModal1(false)
        }else {
            setOpenModal1(true)
            getProducts()
            handleClose()
        }
    }
    const toggleModal2=()=>{
        if(openModal2){
            setOpenModal2(false)
            setNewDate("")
            setNewStartTime("")
            setNewEndTime("")
        }else {
            setOpenModal2(true)
            handleClose()
        }
    }
    const toggleModal3=()=>{
        if(openModal3){
            setOpenModal3(false)
            setNewPh("")
            setNewNitrogen("")
            setNewOrganicCarbon("")
            setNewPhosphorus("")
            setNewLimestone("")

        }else {
            setOpenModal3(true)
            handleClose()
        }
    }

    const getProducts = () => {
        client.GetProductsByFarmer(props.farmer.entity.farmerId)
            .then((response) => {
                if(response.data.code==200){
                    setProducts(response.data.results[0])
                    const productsForSel=response.data.results[0].map((product)=>{
                        return { value:product.productId,label:product.product}})
                    setProductsForSelect(productsForSel)
                }else if(response.data.code==404){
                    setProducts(null)
                    setProductsForSelect(null)
                }else if(response.data.code==400){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                    setProducts(null)
                    setProductsForSelect(null)
                }
            })
            .catch((error) => {
                notify(
                    'Error',
                    'Something went wrong!',
                    {state:"error"}
                )
            })
        ;
    };
    const getProductionsByProduct = (selOption) => {
        client.GetProductionsByFarmAndProduct(props.farmer.entity.farmerId,selOption.value)
            .then((response) => {
                if(response.data.code==200){
                    setProductions(response.data.results[0])

                }else if(response.data.code==404){
                    setProductions(null)
                }else if(response.data.code==400){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                    setProductions(null)
                }
            })
            .catch((error) => {
                notify(
                    'Error',
                    'Something went wrong!',
                    {state:"error"}
                )            })
        ;
    };
    const postMeeting = () => {
        let meeting = {
            date:newDate,
            startTime:newStartTime,
            endTime:newEndTime
        }
        client.PostMeeting(meeting,props.farmer.entity.farmerId)
            .then((response) => {
                if(response.data.code===200){
                    props.getAllFarmer()
                    notify(
                        'Success',
                        'The Meeting was successfully created',
                        {state:"success"}
                    )
                }else if(response.data.code===404){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                }else if(response.data.code===400){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                }
                toggleModal2()
            })
            .catch((error) => {
                notify(
                    'Error',
                    'Something went wrong!',
                    {state:"error"}
                )            })
        ;

    }
    const postSoilData = () => {
        let soilData ={
            ph: newPh,
            nitrogen:newNitrogen,
            phosphorus:newPhosphorus,
            organic_carbon:newOrganicCarbon,
            limestone:newLimestone
        }
        client.PostSoilData(soilData,props.farmer.entity.farmerId)
            .then((response) => {
                if(response.data.code===200){
                    props.getAllFarmer()
                    notify(
                        'Success',
                        'The Soil Data was successfully created',
                        {state:"success"}
                    )
                }else if(response.data.code===404){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                }else if(response.data.code===400){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                }
                toggleModal3()
            })
            .catch((error) => {
                notify(
                    'Error',
                    'Something went wrong!',
                    {state:"error"}
                )
            })
        ;

    }


    const handleChangeSelect = (selOption)=>{
        setSelectedOption(selOption)
        if(selOption.value){
            getProductionsByProduct(selOption)

        }
    }


    const [, setValue] = useState(null);
    const handleChange = (selectedOption) => setValue(selectedOption);
    const ids = ['example1'];

    // Workaround for testing, do not use this
    ids.map((id, i) => {
        const div = document.createElement('div');
        div.setAttribute('id', id);
        document.body.appendChild(div);
        return null;
    });


    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };
    const handleClose = () => {
        setAnchorEl(null);
    };
    const tabPage=()=>{
        if(tabSelected==="production"){
            return(
                <div style={{marginTop:40}}>
                    <div className='bootstrap-select-wrapper'>
                        <label htmlFor='selectExampleClassic'>Product</label>
                        <Select
                            id='selectExampleClassic'
                            value={selectedOption}
                            onChange={handleChangeSelect}
                            options={productsForSelect}
                            placeholder='Choose a product'
                            aria-label='Choose a product'
                        />
                    </div>
                    <Table>
                        <thead>
                        <tr>
                            <th scope='col'>Date</th>
                            <th scope='col' style={{textAlign:"right"}}>Q.ta</th>
                            <th scope='col' width={'50%'} >Note</th>
                        </tr>
                        </thead>
                        <tbody>
                        {tableBodyProduction()}
                        </tbody>
                    </Table>
                </div>
            )
        }else if(tabSelected==="soilData"){
            return(
                <Table>
                    <thead>
                    <tr>
                        <th scope='col' >Data</th>
                        <th scope='col' style={{textAlign:"right"}}>Ph</th>
                        <th scope='col' style={{textAlign:"right"}}>Limestone*</th>
                        <th scope='col' style={{textAlign:"right"}}>Nitrogen*</th>
                        <th scope='col' style={{textAlign:"right"}}>Phosphorus*</th>
                        <th scope='col' style={{textAlign:"right"}}>Organic carbon*</th>
                        <th scope='col' ></th>
                        <th scope='col' ></th>
                    </tr>
                    </thead>
                    <tbody>
                    {tableBodySoilData()}
                    </tbody>
                </Table>
            )
        }
    }
    const tableBodyProduction=()=>{
        if(productions){
            const tableB=productions.map((production)=>{
                return(
                    <tr key={production.productionId}>
                        <td>
                            {production.date}
                        </td>
                        <td style={{textAlign:"right"}}>{production.qta+' Kg'}</td>
                        <td>{production.note}</td>
                    </tr>
                )
            })
            return tableB
        }else{
            return (
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            )
        }
    }
    const tableBodySoilData=()=>{
        if(props.farmer.entity.farm.soilDatas.length>0){
            const tableB=props.farmer.entity.farm.soilDatas.map((data)=>{
                return(
                    <TableRowSoilData
                        data={data}
                        farmerId={props.farmer.entity.farmerId}
                        getAllFarmer={()=>props.getAllFarmer()}
                        toggleModal={()=>toggleModal1()}
                    />
                )
            })
            return tableB
        }else{
            return (
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>

                </tr>
            )
        }
    }

    return(
        <tr>
            <td>{props.farmer.entity.lastName}</td>
            <td>{props.farmer.entity.firstName}</td>
            <td>{props.farmer.entity.farm.address}</td>
            <td style={{textAlign:"center"}}>{props.farmer.allert?
                <Tooltip title={"This farmer has less than two meetings organized this year."}>
                    <IconButton>
                        <Icon
                            className=""
                            icon="it-warning-circle"
                            size=""
                            id={ids[0]}
                        />
                    </IconButton>
                </Tooltip>

                :""
            }</td>
            <td style={{textAlign:"center"}}>
                <IconButton>
                    <Icon
                        className=""
                        icon="it-more-items"
                        size=""
                        onClick={handleClick}

                    />
                </IconButton>
                <Menu
                    id="basic-menu"
                    anchorEl={anchorEl}
                    open={openMenu}
                    onClose={handleClose}
                    MenuListProps={{
                        'aria-labelledby': 'basic-button',
                    }}
                >
                    <MenuItem onClick={() => toggleModal1()} >Profile</MenuItem>
                    <MenuItem onClick={()=> toggleModal2()}>Plan a meeting</MenuItem>
                    <MenuItem onClick={()=>toggleModal3()}>Insert Soil Data</MenuItem>
                </Menu>
            </td>
            <Modal
                isOpen={openModal1}
                toggle={() => toggleModal1()}
                centered
                scrollable
                size={'lg'}
            >
                <ModalHeader toggle={() => toggleModal1()} id='essempio9'>
                    {props.farmer.entity.lastName+" "+props.farmer.entity.firstName}
                </ModalHeader>
                <ModalBody>
                    <dl className='row'>
                        <dt className='col-sm-3'>Aadhaar</dt>
                        <dd className='col-sm-9'>
                            {props.farmer.entity.aadhaar}
                        </dd>
                        <dt className='col-sm-3'>E-mail</dt>
                        <dd className='col-sm-9'>
                            {props.farmer.entity.email}
                        </dd>
                        <dt className='col-sm-3'>Telephone</dt>
                        <dd className='col-sm-9'>
                            {props.farmer.entity.telephone}
                        </dd>
                        <dt className='col-sm-3'>Farm address</dt>
                        <dd className='col-sm-9'>
                            {props.farmer.entity.farm.address}
                        </dd>
                        <dt className='col-sm-3'>Square Kilometer</dt>
                        <dd className='col-sm-9'>
                            {props.farmer.entity.farm.squareKm+" "}km&#178;
                        </dd>
                    </dl>
                    <div style={{marginBottom:20}}>
                        <Button color={tabSelected==="production"?"primary":"secondary"}
                                size='sm'
                                onClick={()=>setTabSelected("production")}
                                style={{marginRight:10}}
                        >
                            Production
                        </Button>
                        <Button color={tabSelected==="soilData"?"primary":"secondary"}
                                size='sm'
                                onClick={()=>setTabSelected("soilData")}
                                style={{marginRight:10}}
                        >
                            Soil Data
                        </Button>
                    </div>
                    {tabPage()}


                </ModalBody>
                <ModalFooter/>
            </Modal>
            <Modal
                isOpen={openModal2}
                toggle={() => toggleModal2()}
                centered
                scrollable
                size={'lg'}
            >
                <ModalHeader toggle={() => toggleModal2()} id='essempio9'>
                    Plan a meeting
                </ModalHeader>
                <ModalBody>
                    <div className='input-group'>
                        <label htmlFor='input-group-1'>Date</label>
                        <input
                            type='date'
                            id='input-group-1'
                            name='input-group-1'
                            value={newDate}
                            onChange={(event)=>setNewDate(event.target.value)}
                        />
                    </div>
                    <div className='input-group'>
                        <label htmlFor='input-group-1'>Start</label>
                        <input
                            type='time'
                            id='input-group-1'
                            name='input-group-1'
                            value={newStartTime}
                            onChange={(event)=>setNewStartTime(event.target.value)}
                        />
                    </div>
                    <div className='input-group'>
                        <label htmlFor='input-group-1'>End</label>
                        <input
                            type='time'
                            id='input-group-1'
                            name='input-group-1'
                            value={newEndTime}
                            onChange={(event)=>setNewEndTime(event.target.value)}
                        />
                    </div>
                </ModalBody>
                <ModalFooter>
                    <Button color='secondary' onClick={() => toggleModal2()}>
                        Close
                    </Button>
                    <Button color='primary' onClick={() => postMeeting()}>
                        OK
                    </Button>
                </ModalFooter>
            </Modal>
            <Modal
                isOpen={openModal3}
                toggle={() => toggleModal3()}
                centered
                scrollable
                size={'lg'}
            >
                <ModalHeader toggle={() => toggleModal3()} id='essempio9'>
                    Insert Soil Data
                </ModalHeader>
                <ModalBody>
                    <div className='form-row'>
                        <Input
                            type='number'
                            label='Ph'
                            id='inputPh'
                            value={newPh}
                            onChange={(event)=>setNewPh(event.target.value)}
                            wrapperClass='col col-md-12'
                        />
                    </div>
                    <div className='form-row'>
                        <Input
                            type='number'
                            label='Limestone [mg/Kg]'
                            id='inputLimestone'
                            value={newLimestone}
                            onChange={(event)=>setNewLimestone(event.target.value)}
                            wrapperClass='col col-md-12'
                        />
                    </div>
                    <div className='form-row'>
                        <Input
                            type='number'
                            label='Nitrogen [mg/Kg]'
                            id='inputNitrogen'
                            value={newNitrogen}
                            onChange={(event)=>setNewNitrogen(event.target.value)}
                            wrapperClass='col col-md-12'
                        />
                    </div>
                    <div className='form-row'>
                        <Input
                            type='number'
                            label='Phosphorus [mg/Kg]'
                            id='inputPhosphorus'
                            value={newPhosphorus}
                            onChange={(event)=>setNewPhosphorus(event.target.value)}
                            wrapperClass='col col-md-12'
                        />
                    </div>
                    <div className='form-row'>
                        <Input
                            type='number'
                            label='Organic carbon [mg/Kg]'
                            id='inputOrganicCarbon'
                            value={newOrganicCarbon}
                            onChange={(event)=>setNewOrganicCarbon(event.target.value)}
                            wrapperClass='col col-md-12'
                        />
                    </div>

                </ModalBody>
                <ModalFooter>
                    <Button color='secondary' onClick={() => toggleModal3()}>
                        Close
                    </Button>
                    <Button color='primary' onClick={() => postSoilData()}>
                        OK
                    </Button>
                </ModalFooter>
            </Modal>
        </tr>
    )

}

function TableRowSoilData(props){
    const [openModalEdit, setOpenModalEdit] = useState(false);
    const [openModalDelete, setOpenModalDelete] = useState(false);

    const [newPh, setNewPh] = useState("");
    const [newLimestone, setNewLimestone] = useState("");
    const [newNitrogen, setNewNitrogen] = useState("");
    const [newPhosphorus, setNewPhosphorus] = useState("");
    const [newOrganicCarbon, setNewOrganicCarbon] = useState("");
    const client=new ApiClient()



    const deleteSoilData=()=>{
        client.DeleteSoilData(props.data.soilDataId)
            .then((response) => {
                if(response.data.code==200){
                    props.getAllFarmer()
                    notify(
                        'Success',
                        'The Soil Data was successfully deleted',
                        {state:"success"}
                    )

                }else if(response.data.code==404){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                }else if(response.data.code==400){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                }
                setOpenModalDelete(!openModalDelete)
                props.toggleModal()
            })
            .catch((error) => {
                notify(
                    'Error',
                    'Something went wrong!',
                    {state:"error"}
                )
            })
        ;

    }
    const putSoilData=()=>{
        let soilData = {
            ph: newPh,
            nitrogen:newNitrogen,
            phosphorus:newPhosphorus,
            organic_carbon:newOrganicCarbon,
            limestone:newLimestone
        }

        client.PutSoilData(soilData,props.farmerId,props.data.soilDataId)
            .then((response) => {
                if(response.data.code===200){
                    props.getAllFarmer()
                    notify(
                        'Success',
                        'The Soil Data was successfully edited',
                        {state:"success"}
                    )
                }else if(response.data.code===404){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                }else if(response.data.code===400){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                }
                handleToggleModalEdit()
                props.toggleModal()
            })
            .catch((error) => {
                notify(
                    'Error',
                    'Something went wrong!',
                    {state:"error"}
                )
            })
        ;

    }

    const handleToggleModalEdit=()=>{
        if(openModalEdit){
            setOpenModalEdit(false)
            setNewPh("")
            setNewLimestone("")
            setNewNitrogen("")
            setNewPhosphorus("")
            setNewOrganicCarbon("")
        }else {
            const soilD=props.data
            setOpenModalEdit(true)
            setNewPh(soilD.ph)
            setNewLimestone(soilD.limestone)
            setNewNitrogen(soilD.nitrogen)
            setNewPhosphorus(soilD.phosphorus)
            setNewOrganicCarbon(soilD.organic_carbon)
        }
    }

    return(
        <tr >
            <td>{props.data.date}</td>
            <td style={{textAlign:"right"}}>{props.data.ph}</td>
            <td style={{textAlign:"right"}}>{props.data.limestone}</td>
            <td style={{textAlign:"right"}}>{props.data.nitrogen}</td>
            <td style={{textAlign:"right"}}>{props.data.phosphorus}</td>
            <td style={{textAlign:"right"}}>{props.data.organic_carbon}</td>
            <td style={{textAlign:"center"}}>
                <IconButton style={{padding:0}}
                                onClick={() =>handleToggleModalEdit()}
                >
                    <Icon
                        className=""
                        color=""
                        icon="it-pencil"
                        size="sm"
                    />
                </IconButton>
            </td>
            <td style={{textAlign:"center"}}>
                <IconButton style={{padding:0}}
                                onClick={() =>setOpenModalDelete(!openModalDelete)}
                >
                    <Icon
                        className=""
                        color=""
                        icon="it-delete"
                        size="sm"
                    />
                </IconButton>
            </td>
            <Modal
                isOpen={openModalDelete}
                toggle={()=>setOpenModalDelete(!openModalDelete)}
                centered
                labelledBy='esempio9'
            >
                <ModalHeader  id='essempio9'>
                    Are you sure you want to delete?
                </ModalHeader>
                <ModalFooter>
                    <Button color='secondary' onClick={()=>setOpenModalDelete(!openModalDelete)}>
                        Close
                    </Button>
                    <Button color='primary' onClick={() => deleteSoilData()}>
                        OK
                    </Button>
                </ModalFooter>
            </Modal>
            <Modal
                isOpen={openModalEdit}
                toggle={() => handleToggleModalEdit()}
                centered
                scrollable
                size={'lg'}
            >
                <ModalHeader toggle={() => handleToggleModalEdit()} id='essempio9'>
                    Edit the SoilData
                </ModalHeader>
                <ModalBody>
                    <div className='form-row'>
                        <Input
                            type='number'
                            label='Ph'
                            id='inputPh'
                            value={newPh}
                            onChange={(event)=>setNewPh(event.target.value)}
                            wrapperClass='col col-md-12'
                        />
                    </div>
                    <div className='form-row'>
                        <Input
                            type='number'
                            label='Limestone [mg/Kg]'
                            id='inputLimestone'
                            value={newLimestone}
                            onChange={(event)=>setNewLimestone(event.target.value)}
                            wrapperClass='col col-md-12'
                        />
                    </div>
                    <div className='form-row'>
                        <Input
                            type='number'
                            label='Nitrogen [mg/Kg]'
                            id='inputNitrogen'
                            value={newNitrogen}
                            onChange={(event)=>setNewNitrogen(event.target.value)}
                            wrapperClass='col col-md-12'
                        />
                    </div>
                    <div className='form-row'>
                        <Input
                            type='number'
                            label='Phosphorus [mg/Kg]'
                            id='inputPhosphorus'
                            value={newPhosphorus}
                            onChange={(event)=>setNewPhosphorus(event.target.value)}
                            wrapperClass='col col-md-12'
                        />
                    </div>
                    <div className='form-row'>
                        <Input
                            type='number'
                            label='Organic carbon [mg/Kg]'
                            id='inputOrganicCarbon'
                            value={newOrganicCarbon}
                            onChange={(event)=>setNewOrganicCarbon(event.target.value)}
                            wrapperClass='col col-md-12'
                        />
                    </div>
                </ModalBody>
                <ModalFooter>
                    <Button color='secondary' onClick={() => handleToggleModalEdit()}>
                        Close
                    </Button>
                    <Button color='primary' onClick={() => putSoilData()}>
                        OK
                    </Button>
                </ModalFooter>
            </Modal>


        </tr>
    )
}




export default function AgronomistFarmers(){
    const [farmers,setFarmers]=useState(null)
    const client=new ApiClient()
    useEffect(()=>{
        getAllFarmer()
    },[])
    const getAllFarmer = () => {
        client.GetFarmerByAgronomist()
            .then((response) => {
                if(response.data.code==200){
                    setFarmers(response.data.results[0])
                }else if(response.data.code==404){
                    setFarmers(null)
                }else if(response.data.code==400){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                    setFarmers(null)
                }
            })
            .catch((error) => {
                notify(
                    'Error',
                    'Something went wrong!',
                    {state:"error"}
                )
            })
        ;
    };
    const tableBody=()=>{
        if(farmers){
            const tableB=farmers.map((farmer)=> {
                    return (
                        <TableRowFarmers farmer={farmer} getAllFarmer={()=>getAllFarmer()}/>
                    )
                }
            )
            return tableB
        }else{
            return (
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>

                </tr>
            )
        }
    }

    return(
        <div>
            <Container style={{paddingTop:20,paddingBottom:20}}>
                <h4 style={{fontSize:35}}>Farmers</h4>
                <Table>
                    <thead>
                    <tr>
                        <th scope='col' >Last Name</th>
                        <th scope='col'>First Name</th>
                        <th scope='col'width={"30%"}>Farm address</th>
                        <th scope='col' width={'5%'} style={{textAlign:"center"}}></th>
                        <th scope='col' width={'20%'} style={{textAlign:"center"}}></th>
                    </tr>
                    </thead>
                    <tbody>
                    {tableBody()}
                    </tbody>
                </Table>
            </Container>
        </div>
    )
}