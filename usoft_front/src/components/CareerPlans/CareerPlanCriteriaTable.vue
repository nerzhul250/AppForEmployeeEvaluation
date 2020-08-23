<template>
    <div>
        <v-simple-table>
        <tbody>
            <tr>
                <td>
                    <h1 class="text-center">Percentages</h1>
                </td>
                <td
                    v-for="(pos,j) in selPos"
                    :key=pos.id
                >
                <v-autocomplete
                    v-model="selPos[j]"
                    :items="positions"
                    hide-no-data
                    hide-selected
                    item-text="name"
                    placeholder="Position"
                    return-object
                    @change="updatePos(j)"
                ></v-autocomplete>
                </td>
            </tr>
            <tr
            v-for="(ind,i) in selInd"
            :key=ind.id
            >
                <td>
                    <v-autocomplete
                        v-model="selInd[i]"
                        :items="indicators"
                        hide-no-data
                        hide-selected
                        item-text="name"
                        placeholder="Indicator"
                        return-object
                        @change="updateIndicator(i)"
                    ></v-autocomplete>
                </td>
                <td
                    v-for="(pos,j) in selPos"
                    :key=pos.id
                >
                    <v-autocomplete
                        v-model="ind_pos_crits[i][j].percent"
                        :items="percentages"
                        hide-no-data
                        hide-selected
                        placeholder="Percentage"
                        return-object
                    ></v-autocomplete>
                    <center>
                        <div class="btn_view_criterias">
                            <v-btn small rounded @click="selectIndPos(i,j)">Criterias</v-btn>
                        </div>
                    </center>
                </td>
            </tr>
        </tbody>
        </v-simple-table>
        <br />

        <div v-if="selectedIndPos">
            <h3>Criterias from</h3>
            <h4>Indicator: {{ind_pos_crits[selectedIndPos[0]][selectedIndPos[1]].indicator.name}}</h4>
            <h4>Position: {{ind_pos_crits[selectedIndPos[0]][selectedIndPos[1]].position.name}}</h4>
            <table>
                <thead>
                    <th class ="txt-center">Criteria description</th>                    
                    <th class ="txt-center">Criteria percentage</th>
                </thead>
                <tbody>
                    <tr
                        v-for="crit in ind_pos_crits[selectedIndPos[0]][selectedIndPos[1]].criterias"
                        :key=crit
                    >
                        <td>
                            <v-text-field
                                v-model="crit.description"
                                label="Criteria description"
                                required
                            ></v-text-field>
                        </td>
                        <td>
                            <v-autocomplete
                                v-model="crit.percent"
                                :items="percentages"
                                hide-no-data
                                hide-selected
                                placeholder="Percentage"
                                return-object
                            ></v-autocomplete>
                        </td>
                    </tr>
                </tbody>
            </table>
            <br />
             <v-btn
                class="mr-4"
                rounded
                @click="addCriteria"
            >
                Add new criteria
            </v-btn>
        </div>

        <br />

        <v-btn
            class="mr-4"
            rounded
            @click="addIndicator"
        >
            Add new Indicator
        </v-btn>
        <v-btn
            class="mr-4"
            rounded
            @click="addPosition"
        >
            Add new Position
        </v-btn>        
    </div>
</template>
<script>
export default {
    data() {
        return{
            selInd:[{id:-1,name:'-1',description:'-1'}],
            selPos:[{id:-1,name:'-1',description:'-1'}],
            selectedIndPos:undefined,
            percentages:[1,2,3,4,5,6,7,8,9,10,
                        11,12,13,14,15,16,17,18,19,20,
                        21,22,23,24,25,26,27,28,29,30,
                        31,32,33,34,35,36,37,38,39,40,
                        41,42,43,44,45,46,47,48,49,50,
                        51,52,53,54,55,56,57,58,59,60,
                        61,62,63,64,65,66,67,68,69,70,
                        71,72,73,74,75,76,77,78,79,80,
                        81,82,83,84,85,86,87,88,89,90,
                        91,92,93,94,95,96,97,98,99,100]
        };
    },
    props: {
        indicators: Array,
        positions: Array,
        ind_pos_crits: Array
    },
    methods:{
        addIndicator(){
            this.selInd.push({
                id:-1,
                name:'-1',
                description:'-1'
            })
            var j
            var newRow=[]
            for (j = 0; j < this.selPos.length; j++) {
                newRow.push({
                    position:this.selPos[j],
                    indicator:this.selInd[this.selInd.length-1],
                    percent:1,
                    orden:j,
                    criterias:[]
                })
            }
            this.ind_pos_crits.push(newRow)
        },
        addPosition(){
            this.selPos.push({
                id:-1,
                name:'-1',
                description:'-1'
            })
            var i
            for(i=0;i<this.selInd.length;i++){
                this.ind_pos_crits[i].push({
                    position:this.selPos[this.selPos.length-1],
                    indicator:this.selInd[i],
                    percent:1,
                    orden:this.selPos.length,
                    criterias:[]
                })
            }
        },
        updatePos(positionIndex){
            var i;
            for (i = 0; i < this.ind_pos_crits.length; i++) {
                this.ind_pos_crits[i][positionIndex].position=this.selPos[positionIndex]; 
            }
        },
        updateIndicator(indicatorIndex){
            var j;
            for (j = 0; j < this.ind_pos_crits[indicatorIndex].length; j++) {
                this.ind_pos_crits[indicatorIndex][j].indicator=this.selInd[indicatorIndex];
            }
        },
        selectIndPos(i,j){
            if(i==-1)this.selectedIndPos=undefined
            this.selectedIndPos=[i,j]
        },
        addCriteria(){
            this.ind_pos_crits[this.selectedIndPos[0]][this.selectedIndPos[1]].criterias.push(
                {
                    description:"",
                    percent:1
                }
            )
        }
    }
}
</script>
<style scoped>
    td, th {
        border: 1px solid #999;
        padding: 0.5rem;
    }
    .btn_view_criterias {
        margin-left: 2%;
        margin-top: 2%;
    }
</style>