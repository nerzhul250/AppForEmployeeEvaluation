// ---- MAKE AN EVALUATION ---
export function sendEvaluation(store, employee, period, careerPlan, evaluator, evaluationObject, comment = "") {
    var payload = {
        employee,
        period,
        careerPlan,
        evaluator,
        evaluationObject,
        comment
    };
    return store.dispatch("evaluation/sendEvaluation", payload);
    //.then((resp)=>{this.synchronized = true;});
}

// ---- CREATE / LOAD AN EVALUATION  ----

/**
  * Obtain the base object for the evaluation of indicators
  * @param {Object} careerPlan Info of indicators and criterias
  * @param {String} charge Name of the charge
  * @param {number} order the index of the charge for the indicator in the career plan
  * @param {number} levels the levels of each charge in the career plan
  * @param {Object} evaluationResult EValuated criterias
  * @return {Object} object to store the evaluation
  *    {indicator_id: {
  *        indicator_id: {string},
  *        percent: {number} (0 - 100),
  *        value: {number},
  *        criteria: {
  *            id:{
  *                id: {string},
  *                percent: {number} (0 - 100),
  *                value: {number},
  *            }
  *        ...
  *        }
  *    }
  *    ...
  *    }
  */
export function baseIndicators(careerPlan, charge = "Student", order = 0, levels = 4, prevEvaluation = undefined) {
    var baseVal = order * levels;
    var indicators = {};
    var evaluationResult = undefined;
    if(prevEvaluation && prevEvaluation.criteria){
        evaluationResult = prevEvaluation.criteria.reduce( (result, c) => {
            result[c.criteria_id] = c;
            return result;
        }, {});
    }
    careerPlan.indicator.forEach(indicator => {
        var crt = {};
        // console.log(indicator.positions[charge].criteria);
        indicator.positions[charge].criteria.forEach(criteria => {
            crt[criteria.id] = {
                criteria_id: criteria.id,
                percent: criteria.percent,
                value: evaluationResult ? evaluationResult[criteria.id].value : baseVal,
                comment: evaluationResult!==undefined? evaluationResult[criteria.id].comment : undefined
            };
        });
        indicators[indicator.indicator_id] = {
            indicator_id: indicator.indicator_id,
            percent: indicator.positions[charge].percent,
            value: baseVal,
            criteria: crt
        };
        if(evaluationResult!==undefined){
            updateIndicarotValue(indicators[indicator.indicator_id]);
        }
    });
    return indicators;
}

// ---- UPDATE NUMERIC VALUES FOR EVALUATION ----

/**
 * Calculate the total score of an evaluation
 * @param {Object} evaluation contains the indicator of an evaluation
*/
export function evaluationScore(evaluation) {
    var score = 0;
    Object.values(evaluation).forEach(indS => {
        score += indS.value * indS.percent;
    });
    score /= 100;
    return score;
}

/**
 * Calculate the value of an indicator with the values of the criteria.
 * @param {Object} indicator
 */
export function updateIndicarotValue(indicator) {
    var indiVal = 0;
    Object.values(indicator.criteria).forEach(criteria => {
        indiVal += criteria.value * criteria.percent;
    });
    indicator.value = indiVal / 100;
    return indicator.value;
}

/**
 * Updates the value of the criteria of an indicator with the value of the indicator.
 * @param {Object} indicator
 */
export function updateCriteriaValues(indicator) {
    const indiVal = indicator.value;
    Object.values(indicator.criteria).forEach(criteria => {
        criteria.value = indiVal;
    });
}

// ---- UPDATE LABELS FOR SCORES ----

/** basic options to evaluate the employee 
 * @param {number} Improve The base score for a charge
 * @return {Array} A list for all available labels for score with their values
 */
function evaluationOptions(Improve = 0) {
    //var less = Improve > 0 ? Improve - 1 : 0;
    return [
        { label: "Inconsistent", value: Improve - 1 },
        { label: "Improve", value: Improve },
        { label: "Achieves", value: Improve + 1 },
        { label: "Exceeds", value: Improve + 2 },
        { label: "Far Exceeds", value: Improve + 3 },
        { label: "More", value: Improve + 4 }
    ]
}

/**
 * get the label corresponding to the given score
 * @param {number} score
 * @param {Array} evaluationOptions all available labels 
 * @returns {string} the label
 */
function scoreLabel(score = 0, options = evaluationOptions()) {
    var base = Math.floor(score);
    var lblItem = options.find(s => s.value == base);
    if (lblItem) return lblItem.label;
    return "Not defined";
}

/**
* Add the values to the option List
* @param {Array} options original List of options
* @param {VarArgs<number>} values values to add
*/
function concatOptions(options = [], values = [], labelFunction = scoreLabel) {
    return options.concat(
        values.map(v => ({
            label: "(" + v + ") " + labelFunction(v),
            value: v,
            disabled: true
        }))
    );
}
const colorScore = {
    "-1": "#C62828",
    Inconsistent: "#C62828",
    Less: "#C62828",
    "0": "#5D4037",
    Improve: "#5D4037",
    "1": "#0D47A1",
    Achieves: "#0D47A1",
    "2": "#0D4C57",
    Exceeds: "#0D4C57",
    "3": "#00897B",
    "Far Exceed": "#00897B",
    "4": "#00BFA5",
    "5": "#00BFA5",   
    More: "#00BFA5",
}


export const labelFunctions = {
    evaluationOptions,
    scoreLabel,
    concatOptions,
    colorScore
}
