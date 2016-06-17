package com.example.android.myapplication;


        import android.graphics.Color;
        import android.support.v7.app.ActionBarActivity;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.FrameLayout;
        import android.widget.LinearLayout;
        import android.widget.TableLayout;
        import android.widget.TextView;
        import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     * 0 - начальная страница
     * 1 - игра Крестики-нолики
     * 2 - игра Пятнашки
     */
    private int thisGame = 0;
    private final static String BUNDLE_KEY_INT_THIS_GAME = "thisGame";

    /*
    ---------------------------------------------------------------------------------------TicTacToe
     */
    private final static String BUNDLE_KEY_ARRAY_TIC_TAC = "keyTicTac";
    private final static String BUNDLE_KEY_TEXT_TIC_TAC  = "textFieldTicTac";
    private final static int SING_CROSS = 1;
    private final static int SING_TOE   = 2;
    public String[] Sings = {"", "X", "O"};

    /**
     * array[0]...array[8] - поле игры 3х3 :   0 = пустая ячейка; 1 = "Х"; 2 = "О"
     * array[9]...array[11] - выигрышный ряд
     * array[12] - победитель : 0 = нет;  1 = "X" победил; 2 = "О" победил
     * array[13] - индикатор игры :  0 = игра окончена; 1 = ход "Х"; 2 = ход "О"
     * array[14] - счетчик ходов игры Крестики-нолики
     */
    private int[] array      = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    private int[] btnsIdTic  = {R.id.btn0,  R.id.btn1, R.id.btn2,
            R.id.btn3,  R.id.btn4,  R.id.btn5,
            R.id.btn6,  R.id.btn7,  R.id.btn8,};
    private Button[] btnsTic = new Button [9];

    TextView tvField;

    /*
    -------------------------------------------------------------------------------------BarleyBrake
     */
    private final static String BUNDLE_KEY_ARRAY_BARLEY = "keyBarleyBrake";
    private final static String BUNDLE_KEY_CNT_BARLEY   = "textCntMovesBarley";
    private int cntMovesBarley = 0;
    /**
     * array[0]...array[15] - поле игры 4х4 :   0 = пустая ячейка;
     * array[16] - индикатор игры :  0 = игра окончена;
     * array[17] - счетчик ходов игры Пятнашки
     */
    private int[] arrBarley     = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    private int[] btnsIdBarley  = {R.id.btn00, R.id.btn01,  R.id.btn02, R.id.btn03,
            R.id.btn04, R.id.btn05,  R.id.btn06, R.id.btn07,
            R.id.btn08, R.id.btn09,  R.id.btn10, R.id.btn11,
            R.id.btn12, R.id.btn13,  R.id.btn14, R.id.btn15};
    private Button[] btnsBarley = new Button [16];

    TextView tvCntMovesBarley;


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState != null)
        {
            this.thisGame = savedInstanceState.getInt(BUNDLE_KEY_INT_THIS_GAME);

            if (this.thisGame == 0)
            {
                setContentView(R.layout.activity_blank);
            }
            else
            if (this.thisGame == 1)
            {
                //----------------------------------------------------------------inRestoreTicTacToe

                this.setContentView(R.layout.activity_main);
                for (int i = 0; i < this.btnsTic.length; i++){
                    this.btnsTic[i] = (Button) this.findViewById(this.btnsIdTic[i]);
                }
                this.tvField = (TextView) findViewById(R.id.text);
                this.array = savedInstanceState.getIntArray(BUNDLE_KEY_ARRAY_TIC_TAC);
                this.tvField.setText(savedInstanceState.getString(BUNDLE_KEY_TEXT_TIC_TAC));

                for (int i = 0; i < this.btnsTic.length; i++) {
                    this.btnsTic[i].setText(String.valueOf(this.getSing(this.array[i])));
                    this.btnsTic[i].setTextColor(Color.BLACK);
                }
                if (this.array[12] != 0 && this.array[13] == 0) {
                    for (int i = 0; i < 3; i++) {
                        this.btnsTic[this.array[9 + i]].setTextColor(Color.RED);
                    }
                }
            }
            else
            if (this.thisGame == 2)
            {
                //--------------------------------------------------------------inRestoreBarleyBrake

                this.setContentView(R.layout.activity_barley);
                for (int i = 0; i < this.btnsIdBarley.length; i++){
                    this.btnsBarley[i] = (Button) this.findViewById(this.btnsIdBarley[i]);
                }
                this.tvCntMovesBarley = (TextView) findViewById(R.id.cntBarleyMoves);
                this.arrBarley = savedInstanceState.getIntArray(this.BUNDLE_KEY_ARRAY_BARLEY);
                this.cntMovesBarley = savedInstanceState.getInt(this.BUNDLE_KEY_CNT_BARLEY);
                this.fillBarleyField();
                this.tvCntMovesBarley.setText(String.valueOf(this.cntMovesBarley));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
    }

    @Override
    public void onSaveInstanceState (Bundle B){
        super.onSaveInstanceState(B);
        B.putInt(MainActivity.BUNDLE_KEY_INT_THIS_GAME, thisGame);

        B.putIntArray(MainActivity.BUNDLE_KEY_ARRAY_TIC_TAC, this.array);
        B.putString(MainActivity.BUNDLE_KEY_TEXT_TIC_TAC, String.valueOf(this.tvField.getText()));

        B.putIntArray(MainActivity.BUNDLE_KEY_ARRAY_BARLEY, this.arrBarley);
        B.putInt(MainActivity.BUNDLE_KEY_CNT_BARLEY,this.cntMovesBarley);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.action_startNewTicTac)
        {
            this.thisGame = 1;
            this.setContentView(R.layout.activity_main);
            for (int i = 0; i < this.btnsTic.length; i++){
                this.btnsTic[i] = (Button) this.findViewById(this.btnsIdTic[i]);
            }
            this.tvField = (TextView) findViewById(R.id.text);
            this.startNewTicTacToe();
            return true;
        }
        if(id == R.id.action_continueTicTac)
        {
            this.thisGame = 1;
            this.setContentView(R.layout.activity_main);
            for (int i = 0; i < this.btnsTic.length; i++){
                this.btnsTic[i] = (Button) this.findViewById(this.btnsIdTic[i]);
            }
            this.tvField = (TextView) findViewById(R.id.text);
            for (int i = 0; i < this.btnsTic.length; i++) {
                this.btnsTic[i].setText(String.valueOf(this.getSing(this.array[i])));
                this.btnsTic[i].setTextColor(Color.BLACK);
            }
            if (this.array[12] != 0 && this.array[13] == 0) {
                for (int i = 0; i < 3; i++) {
                    this.btnsTic[this.array[9 + i]].setTextColor(Color.RED);
                }
            }
            this.tvField.setText("Ход " + String.valueOf(this.getSing(this.array[13])));
            return true;
        }
        if(id == R.id.action_backTicTac)
        {
            this.thisGame = 0;
            this.setContentView(R.layout.activity_blank);
            return true;
        }

        if (id == R.id.action_startNewBarley) {
            this.thisGame = 2;
            this.setContentView(R.layout.activity_barley);
            for (int i = 0; i < this.btnsIdBarley.length; i++){
                this.btnsBarley[i] = (Button) this.findViewById(this.btnsIdBarley[i]);
            }
            this.tvCntMovesBarley = (TextView) findViewById(R.id.cntBarleyMoves);
            this.newBarley();
            this.cntMovesBarley = 0;
            this.tvCntMovesBarley.setText(Integer.toString(this.cntMovesBarley));
            this.fillBarleyField();
            return true;
        }
        if (id == R.id.action_continueBarley) {
            this.thisGame = 2;
            this.setContentView(R.layout.activity_barley);
            for (int i = 0; i < this.btnsIdBarley.length; i++){
                this.btnsBarley[i] = (Button) this.findViewById(this.btnsIdBarley[i]);
            }
            this.tvCntMovesBarley = (TextView) findViewById(R.id.cntBarleyMoves);
            this.fillBarleyField();
            this.tvCntMovesBarley.setText(Integer.toString(this.cntMovesBarley));
            return true;
        }
        if (id == R.id.action_backBarley)
        {
            this.thisGame = 0;
            this.setContentView(R.layout.activity_blank);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //---------------------------------------------------------------------------------------TicTacToe

    public void startNewTicTacToe(){
        this.ClearGame();
        this.tvField.setText("Для начала игры нажмите любую кнопку.");
    }

    public void gameTicTac(View v){
        int id = v.getId();
        int idInArray = 0;
        Button btn = (Button) v.findViewById(id);

        for (int i = 0; i < this.btnsIdTic.length; i++)
        {
            if (this.btnsIdTic[i] == id)
            {
                idInArray = i;
                break;
            }
        }
        met:
        {
            if (this.array[14] == 0)
            {
                this.ClearGame();
            }
            else {
                if (this.array[idInArray] > 0)
                {
                    this.tvField.setText("Вы выбрали не пустую ячейку. Повторите свой ход. Ход " + String.valueOf(this.getSing(this.array[13])));
                    Toast.makeText(this, " Вы выбрали не пустую ячейку. Повторите свой ход. Ход " + String.valueOf(this.getSing(this.array[13])), Toast.LENGTH_SHORT).show();
                    this.array[14] --;
                } else {
                    this.MoveCrossOrToe();
                    this.array[idInArray] = this.array[13];
                    btn.setText(String.valueOf(this.getSing(this.array[13])));
                    this.isWin();

                    if (this.array[12] != 0 && this.array[13] == 0) {
                        for (int i = 0; i < 3; i++) {
                            this.btnsTic[this.array[9 + i]].setTextColor(Color.RED);
                        }
                        Toast.makeText(this, "Победил " + String.valueOf(this.getSing(this.array[9])), Toast.LENGTH_SHORT).show();
                        this.tvField.setText("Для начала новой  игры нажмите любую кнопку.");
                        this.array[14] = 0;
                        break met;
                    }

                    if (this.array[14] == 9 && this.array[12] == 0) {
                        Toast.makeText(this, "Ничья", Toast.LENGTH_SHORT).show();
                        this.tvField.setText("Для начала новой игры нажмите любую кнопку.");
                        this.array[14] = 0;
                        break met;
                    }
                }
            }
            this.array[14] ++;
            this.MoveCrossOrToe();
            this.tvField.setText("Ход " + String.valueOf(this.getSing(this.array[13])));
        }
    }

    public String getSing(int arraySingsIndex)  {
        return this.Sings[arraySingsIndex];
    }

    public void ClearGame(){
        for (int i = 0 ; i < this.array.length; i++ )
        {
            this.array[i] = 0;
        }

        for (int i = 0; i < 9; i++)
        {
            btnsTic[i].setText("");
            btnsTic[i].setTextColor(Color.BLACK);
        }
    }

    public void MoveCrossOrToe(){
        if (this.array[14] % 2 == 0)
        {
            this.array[13] = SING_TOE;
        }
        else
        {
            this.array[13] = SING_CROSS;
        }
    }

    public void isWin (){
        for (int i = 1; i < 8; i += 3)
        {
            if (this.array[i-1] == this.array[i])
            {
                if (this.array[i] == this.array[i+1]){
                    this.array[9] = i-1;
                    this.array[10] = i;
                    this.array[11] = i+1;
                    this.array[12] = this.array[i];
                    if (this.array[12] != 0)
                    {
                        this.array[13] = 0;
                    }
                    break;
                }
            }
        }

        for (int i = 3; i < 6; i ++)
        {
            if (this.array[i-3] == this.array[i])
            {
                if (this.array[i] == this.array[i+3]){
                    this.array[9] = i-3;
                    this.array[10] = i;
                    this.array[11] = i+3;
                    this.array[12] = this.array[i];
                    if (this.array[12] != 0)
                    {
                        this.array[13] = 0;
                    }
                    break;
                }
            }
        }

        for (int i = 2; i < 5; i += 2)
        {
            if (this.array[4-i] == this.array[4])
            {
                if (this.array[4] == this.array[4+i]) {
                    this.array[9] = 4 - i;
                    this.array[10] = 4;
                    this.array[11] = 4 + i;
                    this.array[12] = this.array[4];
                    if (this.array[12] != 0)
                    {
                        this.array[13] = 0;
                    }
                    break;
                }
            }
        }
    }


    /*
    -------------------------------------------------------------------------------------BarleyBrake
     */
    @Override
    public void onClick(View v) {

        int empty = 0;
        int touch = 0;
        for (int i = 0; i < 16; i++)
        {
            if(this.arrBarley[i] == 0)
            {
                empty = i;
            }

            if(this.btnsBarley[i].getId() == v.getId())
            {
                touch = i;
            }
        }

        boolean canMoveBarley = false;

        for (int i = 0; i < 13; i+=4)
        {
            for (int j = 0; j < 4 ; j++)
            {
                if (canMoveBarley == true)
                {
                    break;
                }
                else
                if( (i+j) == touch)
                {
                    for ( j = 0; j < 4 ; j++)
                    {
                        if((i+j) == empty)
                        {
                            canMoveBarley = true;
                            break;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 16; j += 4)
            {
                if (canMoveBarley == true)
                {
                    break;
                }
                else
                if( (i+j) == touch)
                {
                    for ( j = 0; j < 16 ; j+=4)
                    {
                        if((i+j) == empty)
                        {
                            canMoveBarley = true;
                            break;
                        }
                    }
                }
            }
        }

        int z = 0;
        int j = 0;

        if (canMoveBarley == true)
        {
            if ((int)Math.abs(empty - touch) < 4)
            {
                z =  Math.abs(empty - touch);
                j = 1;
            }
            else
            {
                z =  Math.abs(empty - touch)/ 4;
                j = 4;
            }

            if(empty < touch)
            {
                System.out.println("empty < touch");
                for(int i = 0; i < z ; i++)
                {
                    int tmp = this.arrBarley[empty + j];
                    this.arrBarley [empty + j] = this.arrBarley[empty];
                    this.arrBarley [empty] = tmp;
                    empty += j;
                }
            }
            else
            {
                System.out.println("empty > touch");
                for(int i = 0; i < z ; i++)
                {
                    int tmp = this.arrBarley[empty - j];
                    this.arrBarley [empty - j] = this.arrBarley[empty];
                    this.arrBarley [empty] = tmp;
                    empty -= j;
                }
            }

            this.cntMovesBarley ++;
            this.tvCntMovesBarley.setText(String.valueOf(this.cntMovesBarley));
        }
        else
        {
            Toast.makeText(this, "Неверный ход!", Toast.LENGTH_SHORT).show();
        }

        this.fillBarleyField();

        if (this.checkBarleyWin() == true)
        {
            Toast.makeText(this, "Вы выиграли!", Toast.LENGTH_SHORT).show();
        }
    }

    public void fillBarleyField(){
        for (int i = 0; i < this.btnsBarley.length; i++)
        {
            if(this.arrBarley[i] > 0)
            {
                this.btnsBarley[i].setText(String.valueOf(this.arrBarley[i]));
                this.btnsBarley[i].setBackgroundColor(getResources().getColor(R.color.colorButtonBarley));
            }
            else
            {
                this.btnsBarley[i].setText("");
                this.btnsBarley[i].setBackgroundColor(getResources().getColor(R.color.colorButtonEmpty));
            }
        }
    }

    public void newBarley(){
        int cnt = 0;
        int cntDouble = 0;

        do
        {
            int x = (int) (Math.random() * 16);

            for (int i = 0; i < cnt; i++)
            {
                if (this.arrBarley[i] == x)
                {
                    cntDouble ++;
                }
            }

            if (cntDouble > 0)
            {
                cntDouble = 0;
                continue;
            }

            this.arrBarley[cnt] = x;

            cnt++;
        }
        while(cnt != 16);
    }

    public boolean checkBarleyWin(){
        boolean isWin = false;
        int[] win = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};
        isWin = this.arrBarley.equals(win);
        return isWin;
    }
}

